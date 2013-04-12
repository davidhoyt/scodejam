package scodejam

trait OutputProcessor {
  def reset
  def process(writer: java.io.PrintWriter)(result: String): Unit
}
