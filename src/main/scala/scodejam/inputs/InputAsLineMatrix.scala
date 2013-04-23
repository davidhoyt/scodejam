package scodejam.inputs

import scodejam.StringUtil._

/**
 * The first line of the input gives the number of test cases, T.
 * T test cases follow. Each test case begins with a line containing two numbers: N and M.
 * Next follow N lines, with the ith line containing M numbers ai,j each, the number ai,j
 * describing the desired height of the grass in the jth square of the ith row.
 *
 * e.g.:
 * 3          <-- # test cases
 * 3 3        <-- # of lines, # of values per line
 * 2 1 2
 * 1 1 1
 * 2 1 2
 * 5 5        <-- start of next test case
 * 2 2 2 2 2
 * 2 1 1 1 2
 * 2 1 2 1 2
 * 2 1 1 1 2
 * 2 2 2 2 2
 * 1 3
 * 1 2 1
 */
object InputAsLineMatrix extends InputWithVector {
  override def determineNextCaseSize(iter: Iterator[String]): Int = iter.next().toIntVector(0) //Get the first # from the input
}
