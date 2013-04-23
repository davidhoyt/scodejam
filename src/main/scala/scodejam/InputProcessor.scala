package scodejam

trait InputProcessor {
  type FnInputProcessor = Iterator[String] => Any
  type FnInputCallback = Any => Unit

  def process(settings: CodeJamSettings, iter: Iterator[String])(fn: => FnInputProcessor)(fnCallback: FnInputCallback): Unit
}
