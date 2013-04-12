package scodejam

import scala.language.reflectiveCalls

object Utils {
  def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    using(new java.io.PrintWriter(f)) { p =>
      op(p)
    }
  }
}
