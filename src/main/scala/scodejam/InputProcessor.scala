package scodejam

trait InputProcessor {
  type FnInputProcessor = Vector[String] => String
  type FnInputCallback = String => Unit

  def process(settings: CodeJamSettings, iter: Iterator[String])(fn: => FnInputProcessor)(fnCallback: FnInputCallback): Unit
}
