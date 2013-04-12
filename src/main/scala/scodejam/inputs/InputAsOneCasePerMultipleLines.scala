package scodejam.inputs

import scodejam.InputProcessor

class InputAsOneCasePerMultipleLines(private[this] val linesPerCase: Int) extends StandardInputProcessor {
  if (linesPerCase < 1)
    throw new IllegalArgumentException("There must be at least 1 line per case")

  override def determineExpectedNumberOfCases(iter: Iterator[String]) = iter.next().toInt
  override def determineNextCaseSize(iter: Iterator[String]): Int = linesPerCase //Deliberately does not read anything from the iterator
  override def extractNextCase(iter: Iterator[String]): Vector[String] = iter.take(linesPerCase).toVector
  override def caseSizeIsCorrect(case_content: Vector[String]): Boolean = case_content.size == linesPerCase
}

object InputAsOneCasePerMultipleLines {
  def apply(linesPerCase: Int = 1): InputProcessor = new InputAsOneCasePerMultipleLines(linesPerCase)
}
