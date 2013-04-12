
import scodejam._
import scodejam.inputs._
import scodejam.Assert._

class CodeJam extends AutomaticCodeJamInputs {
  override implicit val settings = CodeJamSettings(showProgress = true)
  override implicit val inputProcessor = InputAsOneCasePerMultipleLines(linesPerCase = 1)

  override def solveForCase(input: Vector[String]): String = {
    //assertNotEquals(true, true)
    "PROCESSED: " + input
    throw new IllegalArgumentException("DOH")
  }
}