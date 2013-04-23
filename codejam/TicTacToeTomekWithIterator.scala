
import scala._
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Utils._
import scodejam.Assert._
import scodejam.TupleUtil._
import scodejam.StringUtil._
import scodejam.BigIntUtil._

class TicTacToeTomekWithIterator extends AutomaticCodeJamInputs {
  override val problemSet  = "A"
  override val problemName = "Tic-Tac-Toe-Tomek-With-Iterator"

  override val settings = CodeJamSettings(skip = true, submitter = "dhoyt", maxWrongCases = 1, showProgress = true)

  def findPlayer(board: Seq[String], s: Seq[(Int, Int)]): Option[Char] = {
    s.find(_ match { case (r,c) => board(r)(c) != '.' && board(r)(c) != 'T' }).map(_ match { case (r,c) => board(r)(c) })
  }

  override def solveForCase(input: Iterator[String]): Any = {
    val board = input.take(4).toVector
    val empty = input.take(1).toVector

    val strategies = Seq(
      Seq((0, 3), (1, 2), (2, 1), (3, 0)),

      Seq((0, 0), (0, 1), (0, 2), (0, 3)),
      Seq((1, 0), (1, 1), (1, 2), (1, 3)),
      Seq((2, 0), (2, 1), (2, 2), (2, 3)),
      Seq((3, 0), (3, 1), (3, 2), (3, 3)),

      Seq((0, 0), (1, 0), (2, 0), (3, 0)),
      Seq((0, 1), (1, 1), (2, 1), (3, 1)),
      Seq((0, 2), (1, 2), (2, 2), (3, 2)),
      Seq((0, 3), (1, 3), (2, 3), (3, 3)),

      Seq((0, 0), (1, 1), (2, 2), (3, 3))
    )


    val result = strategies.flatMap { s =>
      findPlayer(board, s).map { player =>
        val found = s.forall(_ match {
          case (r, c) => {
            val ch = board(r)(c)
            ch == player || ch == 'T'
          }
        })
        if (found)
          Some(player)
        else
          None
      }
    }.flatten.headOption

    result match {
      case Some(player) => "%c won".format(player)
      case _ if board.exists(_.exists(_ == '.')) => "Game has not completed"
      case _ => "Draw"
    }
  }
}