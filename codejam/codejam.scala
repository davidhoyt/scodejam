
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Utils._
import scodejam.Assert._
import scodejam.TupleUtil._
import scodejam.StringUtil._

class CodeJam extends AutomaticCodeJamInputs {
  override implicit val settings = CodeJamSettings(maxWrongCases = 1, showProgress = true)
  override implicit val inputProcessor = InputAsOneCasePerMultipleLines(linesPerCase = 1)
  override implicit val outputProcessor = OutputAsOneCasePerLine()

  override val submitter = "jdoe"
  override val problemName = "speaking-in-tongues"

  override def onComplete(): Unit = zipProject()

  override def solveForCase(input: Vector[String]): String = {
    //assertEquals("2 3 4".toIntVector, Vector(2, 3, 4))
    val char_map = ("ejpmyslckdxvnribtahwougfqz" -> "ourlangeismpbtdhwyxfkjvczq").toCharMap

    val first = input(0)
    first.map(c => char_map.getOrElse(c, c))
  }
}