
import scala._
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Utils._
import scodejam.Assert._
import scodejam.TupleUtil._
import scodejam.StringUtil._
import scodejam.BigIntUtil._

/**
 * Problem defined at:
 *   https://code.google.com/codejam/contest/1460488/dashboard
 */
class SpeakingInTongues extends AutomaticCodeJamInputs {
  override val problemSet  = "Z"
  override val problemName = "Speaking-In-Tongues"

  override val settings = CodeJamSettings(skip = false, submitter = "dhoyt", maxWrongCases = 1, showProgress = true)

  override def solveForCase(input: Iterator[String]): Any = {
    val char_map = ("ejpmyslckdxvnribtahwougfqz" -> "ourlangeismpbtdhwyxfkjvczq").toCharMap

    val first = input.take(1).toVector(0)
    first.map(c => char_map.getOrElse(c, c))
  }
}