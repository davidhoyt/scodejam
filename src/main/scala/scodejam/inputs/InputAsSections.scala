package scodejam.inputs

import scodejam.StringUtil._
import scodejam.InputProcessor

/**
 * The first line of the input gives the number of test cases, T.
 * T test cases follow. Each test case begins with a single line containing multiple positive
 * integers (e.g. K and N), representing the # of lines per section needing to be read in.
 *
 * e.g.:
 * 3
 * 3 3

 * 3         <-- # test cases
 * 1 4       <-- # of lines section 1, # of lines section 2
 * 1
 * 1 0
 * 1 2 1 3
 * 2 0
 * 3 1 2
 * 3 3       <-- next test case
 * 1 1 1
 * 1 0
 * 1 0
 * 1 0
 * 1 1
 * 2
 * 0 0
 */
class InputAsSections extends InputWithVector {
  private[this] var case_header: String = ""

  override def determineNextCaseSize(iter: Iterator[String]): Int = {
    case_header = iter.next()
    case_header.toIntVector.sum + 1
  }

  override def extractNextCase(expected_case_size: Int, iter: Iterator[String]): Vector[String] = {
    val next_case = iter.take(expected_case_size - 1).toVector
    Vector(case_header) ++ next_case
  }

  override def caseSizeIsCorrect(expected_case_size: Int, case_content: Vector[String]): Boolean = {
    expected_case_size == case_content.size
  }
}

object InputAsSections extends InputAsSections {
  def apply(): InputProcessor = new InputAsSections
}
