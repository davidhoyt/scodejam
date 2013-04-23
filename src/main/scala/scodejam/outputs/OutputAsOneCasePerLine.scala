package scodejam.outputs

import scodejam.OutputProcessor

class OutputAsOneCasePerLine() extends OutputProcessor {
  private[this] var case_count = 1L

  override def reset = case_count = 1L

  override def process(writer: java.io.PrintWriter)(result: Any): Unit = {
    writer.println("Case #%d: %s".format(case_count, result.toString))
    writer.flush()
    case_count += 1
  }
}

object OutputAsOneCasePerLine extends OutputAsOneCasePerLine {
  def apply(): OutputAsOneCasePerLine = new OutputAsOneCasePerLine()
}