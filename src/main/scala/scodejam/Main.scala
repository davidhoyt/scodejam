package scodejam

import java.io.File
import scala.language.implicitConversions
import com.googlecode.scalascriptengine._

object Main extends App {
  // the source directory
  val sourceDir = new File(StandardCodeJamSettings.codeJamSourceDir)
  // compilation classpath
  val compilationClassPath = ScalaScriptEngine.currentClassPath
  // runtime classpath (empty). All other classes are loaded by the parent classloader
  val runtimeClasspath = Set[File]()
  // the output dir for compiled classes
  val outputDir = new File(System.getProperty("java.io.tmpdir"), "scala-script-engine-classes")
  outputDir.mkdir

  val sse = new ScalaScriptEngine(Config(
    List(SourcePath(sourceDir, outputDir)),
    compilationClassPath,
    runtimeClasspath
  )) with RefreshAsynchronously with FromClasspathFirst {
    val recheckEveryMillis: Long = 2000 // each file will only be checked maximum once per second
  }

  // delete all compiled classes (i.e. from previous runs)
  sse.deleteAllClassesInOutputDirectory

  var latest_version = 0
  while (true) {
    try {
      val next_version = sse.refresh.version
      if (next_version != latest_version) {
        latest_version = next_version

        val t = sse.newInstance[ScalaScript]("CodeJam") //class name (w/o package)

        println()
        println("==== CODE VERSION %d ====".format(latest_version))
        t.run
      }
    } catch {
      case ce: CompilationError => {
        Console.err.println()
        Console.err.println("==== ERROR COMPILING CODE ====")
        Console.err.println(ce.getMessage)
      }
      case t: Throwable => {
        Console.err.println()
        Console.err.println("==== ERROR RUNNING CODE VERSION %d ====".format(latest_version))
        Console.err.println(t.getMessage)
      }
    }

    Thread.sleep(2000)
  }
}
