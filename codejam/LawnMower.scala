
import scala._
import scala.collection.mutable
import scala.collection.immutable
import scodejam._
import scodejam.inputs._
import scodejam.outputs._
import scodejam.Utils._
import scodejam.Assert._
import scodejam.TupleUtil._
import scodejam.StringUtil._

class LawnMower extends AutomaticCodeJamInputs {
  override implicit val settings = CodeJamSettings(skip = true, submitter = "dhoyt", maxWrongCases = 1, showProgress = true)
  override implicit val inputProcessor = InputAsLineMatrix
  override implicit val outputProcessor = OutputAsOneCasePerLine

  override val problemSet  = "B"
  override val problemName = "Lawnmower"

  override def onComplete(): Unit = doNothing() // zipProject()

  def printLawn(lawn: mutable.ArrayBuffer[mutable.ArrayBuffer[Int]]) = {
    lawn.foreach { r =>
      r.foreach { c =>
        printf("%3d ", c)
      }
      println()
    }
  }

  def updateLawn(r: Int, c: Int, value: Int, lawn: Vector[Vector[Int]]): Vector[Vector[Int]] = {
    lawn.updated(r, lawn(r).updated(c, value))
  }

  type Lawn = mutable.ArrayBuffer[mutable.ArrayBuffer[Int]]
  type Pattern = Vector[Vector[Int]]

  override def solveForCase(processedInput: Iterator[String]): Any = {
    val input = processedInput.toVector
    val n = input.size
    val m = input(0).toIntVector.size

    val lawn = mutable.ArrayBuffer.fill(n, m)(100)
    val pattern = input.map(_.toIntVector)

    var result = true

    def x(value: Int, horizontal: Boolean, row: Int, col: Int, lawn: Lawn): Boolean = {
      val l = lawn.clone()
      if (horizontal) {
        var i = 0
        while(i < m) {
          if (value < l(row)(i)) {
            l(row)(i) = value
          }
          i += 1
        }
      } else {
        var i = 0
        while(i < n) {
          if (value < l(i)(col)) {
            l(i)(col) = value
          }
          i += 1
        }
      }

      printLawn(l)
      println("---")

      var found = false
      for {
        horiz <- Seq(true, false)
        r <- 0 until n
        c <- 0 until m
        if r == 0 || c == 0
        if r != row && c != row
        if l(r)(c) != pattern(r)(c)
      } {
        x(pattern(r)(c), horiz, r, c, l)
      }
      found
    }

    for {
      horiz <- Seq(true, false)
      r <- 0 until n
      c <- 0 until m
      if r == 0 || c == 0
    } {
      if (x(pattern(r)(c), horiz, r, c, lawn))
        result = true
    }
//
//    for {
//      step <- 0 until (n / 2 + 1)
//      r <- step until n - step
//      c <- step until m - step
//      if r == step || r == n - step - 1 || c == step || c == m - step - 1
//    } {
//      if (lawn(r)(c) >=  pattern(r)(c)) {
//        lawn(r)(c) = pattern(r)(c)
//      } else {
//        result = false
//      }
//
//      if (r == n - step - 1 && c == m - step - 1) {
//        printLawn(lawn)
//        println("---")
//      }
//    }
//
//    println("RESULT: " + result)

    if (result) "YES" else "NO"
  }
}