
import scala._
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Utils._
import scodejam.Assert._
import scodejam.TupleUtil._
import scodejam.StringUtil._

class Treasure extends AutomaticCodeJamInputs {
  override implicit val settings = CodeJamSettings(skip = true, submitter = "dhoyt", maxWrongCases = 1, showProgress = true)
  override implicit val inputProcessor = new TreasureInput
  override implicit val outputProcessor = OutputAsOneCasePerLine()

  override val problemSet  = "D"
  override val problemName = "Treasure"

  override def onComplete(): Unit = doNothing() // zipProject()

  override def solveForCase(processedInput: Iterator[String]): Any = {
    val input = processedInput.toVector
    var chest_count = 0
    val sections = input(0).toIntVector
    val starting_keys:KeyChain = input.drop(1).take(1).map(_.toIntVector).flatten.groupBy(value => value).mapValues(_.length)
    val starting_chests:Vector[Chest] = input.drop(2).take(sections(1)).map(_.toIntVector).map(_ match {
      case Seq(keyTypeToOpen, _, rest @ _*) => {
        chest_count += 1
        Chest(chest_count - 1, chest_count, false, keyTypeToOpen.asInstanceOf[Int], rest.asInstanceOf[Seq[Int]].groupBy(value => value).mapValues(_.length))
      }
    })

    def attempt(chests: Vector[Chest], keys: KeyChain, order: Seq[Int], solutions: Seq[Seq[Int]]): Seq[Seq[Int]] = {
      println("ORDER: " + order)
      println("KEY CHAIN: " + keys)
      printChests(chests)

      if (allChestsOpened(chests)) {
        order.reverse +: solutions
      } else {
        (
          for {
            chest  <- findNextPossibleChests(chests, keys)
            chest  <- Some(chest.copy(opened = true))
            chests <- Some(chests.updated(chest.index, chest))
            keys   <- Some(updateKeyChainForChest(chest, keys)) if keys(chest.keyTypeToOpen) > 0
            keys   <- Some(keys.updated(chest.keyTypeToOpen, keys(chest.keyTypeToOpen) - 1))
          } yield attempt(chests, keys, chest.number +: order, solutions)
        ).flatten.toSeq
      }
    }

    //Provides lexicographical sorting implicit
    import scala.math.Ordering.Implicits._

    //Find an answer
    val results = attempt(starting_chests, starting_keys, Seq(), Seq())

    //Find the first answer lexicographically sorted
    val answer = results.sorted.headOption
    if (answer.isDefined)
      answer.mkString(" ")
    else
      "IMPOSSIBLE"
  }

  type KeyChain = Map[Int, Int]

  case class Chest(val index:Int, val number:Int, val opened:Boolean, val keyTypeToOpen:Int, val keyTypesInside:KeyChain)

  def printChests(chests: Iterable[Chest]) = {
    printf("%s | %s | %s | %s", "Chest Number", "Opened", "Key Type To Open Chest", "Key Types Inside")
    println()
    println("-------------+--------+------------------------+------------------")
    chests.foreach { chest =>
      printf("%12d | %6b | %22d | %s", chest.number, chest.opened, chest.keyTypeToOpen, if (chest.keyTypesInside.isEmpty) "None" else chest.keyTypesInside.mkString(", "))
      println()
    }
  }

  def findNextPossibleChests(chests: Iterable[Chest], keys: KeyChain): Iterable[Chest] = keys.map(_ match {
    case (type_of_key, number_of_keys) => {
      chests.filter(chest => !chest.opened && chest.keyTypeToOpen == type_of_key && keys.getOrElse(type_of_key, 0) > 0)
    }
  }).flatten

  def allChestsOpened(chests: Iterable[Chest]): Boolean = chests.forall(_.opened)

  def updateKeyChainForChest(chest: Chest, keys: KeyChain): KeyChain = {
    keys ++ chest.keyTypesInside.map { case (k,v) => k -> (v + keys.getOrElse(k,0)) }
  }

  class TreasureInput extends InputWithVector {
    private[this] var case_header: String = ""

    override def determineNextCaseSize(iter: Iterator[String]): Int = {
      import scodejam.StringUtil._
      case_header = iter.next()
      case_header.toIntVector(1) + 2
    }

    override def extractNextCase(expected_case_size: Int, iter: Iterator[String]): Vector[String] = {
      val next_case = iter.take(expected_case_size - 1).toVector
      Vector(case_header) ++ next_case
    }
  }
}