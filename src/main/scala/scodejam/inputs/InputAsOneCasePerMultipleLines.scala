package scodejam.inputs

import scodejam.InputProcessor

class InputAsOneCasePerMultipleLines(private[this] val linesPerCase: Int) extends InputWithVector {
  if (linesPerCase < 1)
    throw new IllegalArgumentException("There must be at least 1 line per case")

  override def determineNextCaseSize(iter: Iterator[String]): Int = linesPerCase //Deliberately does not read anything from the iterator
}

object InputAsOneCasePerMultipleLines {
  def apply(linesPerCase: Int = 1): InputProcessor = new InputAsOneCasePerMultipleLines(linesPerCase)
}
