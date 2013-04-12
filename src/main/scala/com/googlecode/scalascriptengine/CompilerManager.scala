package com.googlecode.scalascriptengine

import tools.nsc._
import java.io.File

import scala.tools.nsc.reporters.{AbstractReporter, Reporter}
import scala.reflect.internal.util.Position

/**
 * manages the scala compiler, taking care of setting the correct compiler parameters
 * and reporting errors.
 *
 * @author kostantinos.kougios
 *
 *         22 Dec 2011
 */
protected class CompilerManager(sourcePaths: List[SourcePath], classPaths: Set[File], sse: ScalaScriptEngine) extends Logging {

  private def acc(todo: List[SourcePath], done: List[SourcePath]): List[(SourcePath, (Global, Global#Run))] = todo match {
    case Nil => Nil
    case h :: t =>
      val settings = new Settings(s => {
        error("errors report: " + s)
      })
      settings.sourcepath.tryToSet(h.sourceDir.getAbsolutePath :: Nil)
      val cp = classPaths ++ done.map(_.targetDir)
      settings.classpath.tryToSet(List(cp.map(_.getAbsolutePath).mkString(File.pathSeparator)))
      settings.outdir.tryToSet(h.targetDir.getAbsolutePath :: Nil)

      val g = new Global(settings, new CompilationReporter)
      val run = new g.Run
      (h, (g, run)) :: acc(t, h :: done)

  }

  val runMap = acc(sourcePaths, Nil).toMap

  def compile(allFiles: List[String]) = {

    def doCompile(sp: SourcePath, cp: Set[File]) {
      val (g, run) = runMap(sp)

      val phase = run.phaseNamed("typer")
      val cps = new CompilationPlugins(g, sse)
      cps.Component.newPhase(phase)

      val rootPath = sp.sourceDir.getAbsolutePath
      val files = allFiles.filter(_.startsWith(rootPath))
      run.compile(files)
    }

    def all(todo: List[SourcePath], done: List[SourcePath]) {
      todo match {
        case Nil =>
        // nop
        case h :: t =>
          doCompile(h, classPaths ++ done.map(_.targetDir))
          all(t, h :: done)
      }
    }

    all(sourcePaths, Nil)
  }

}

class CompilationError(msg: String) extends RuntimeException(msg)

private class CompilationReporter extends Reporter with Logging {
  var firstError = false

  protected override def info0(pos: Position, msg: String, severity: Severity, force: Boolean): Unit = {
    synchronized {
      if (!firstError && severity == ERROR) {
        firstError = true
        throw new CompilationError(Position.formatMessage(pos, msg, true))
      }
    }
  }

  override def hasErrors: Boolean = false
}