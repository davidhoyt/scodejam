
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Assert._

class CodeJam extends AutomaticCodeJamInputs {
  override implicit val settings = CodeJamSettings(showProgress = true)
  override implicit val inputProcessor = InputAsOneCasePerMultipleLines(linesPerCase = 1)
  override implicit val outputProcessor = OutputAsOneCasePerLine()

  override def solveForCase(input: Vector[String]): String = {
    //assertNotEquals(true, true)

    //Reverse words
    val first = input(0)
    first.reverse.split(""" """).map(_.reverse).foldLeft("")(_ + " " + _)
  }
}