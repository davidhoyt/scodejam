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

  val classpath = List(outputDir)// ++ compilationClassPath

  val sse = new ScalaScriptEngine(Config(
    List(SourcePath(sourceDir, outputDir)),
    compilationClassPath,
    runtimeClasspath
  )) with RefreshAsynchronously with FromClasspathFirst {
    val recheckEveryMillis: Long = 1000 // each file will only be checked maximum once per second
  }

  // delete all compiled classes (i.e. from previous runs)
  sse.deleteAllClassesInOutputDirectory

  var latest_version = 0
  while (true) {
    try {
      val next_code_version = sse.refresh
      val next_version = next_code_version.version
      if (next_version != latest_version) {
        latest_version = next_version

        sourceDir.listFiles().foreach { f =>
          val name = f.getName
          val expected_class_name = name.substring(0, name.length - ".scala".length)

          if (name.endsWith(".scala")) {
            try {
              //Thread.currentThread().setContextClassLoader(next_code_version.classLoader)
              //val classes = ClassFinder(classpath).getClasses()
              //val found = ClassFinder.concreteSubclasses(classOf[ScalaScript].getName, classes)

              val t = sse.newInstance[ScalaScript](expected_class_name) //class name (w/o package)

              val label = t match {
                case c:AutomaticCodeJamInputs if t.isInstanceOf[AutomaticCodeJamInputs] => c.problemName
                case _ => expected_class_name
              }

              println()
              println("==== CODE VERSION %d [%s] ====".format(latest_version, label))
              t.run

            } catch {
              case t: Throwable => {
                Console.err.println()
                Console.err.println("==== ERROR RUNNING CODE VERSION %d [%s] ====".format(latest_version, expected_class_name))
                Console.err.println(t.getMessage)
              }
            }
          }
        }
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

    Thread.sleep(1000)
  }
}
