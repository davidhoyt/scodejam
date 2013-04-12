package scodejam

trait InputProcessor {
  type FnInputProcessor = Vector[String] => String

  def process(settings: CodeJamSettings, iter: Iterator[String], fn: => FnInputProcessor): Unit
}
