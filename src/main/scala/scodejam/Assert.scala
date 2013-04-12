package scodejam

class AssertError(val message: String) extends RuntimeException(message)

object Assert {
  def assertEquals[T](expected: T, actual: T, message: String = "") = if (expected != actual) throw new AssertError(if (message != "") message else "expected " + expected.toString + ", actual " + actual.toString)
  def assertNotEquals[T](expected: T, actual: T, message: String = "") = if (expected == actual) throw new AssertError(if (message != "") message else "expected " + expected.toString + ", actual " + actual.toString)

  def assertTrue[T <: Boolean](actual: T, message: String = "") = if (!actual) throw new AssertError(if (message != "") message else "expected true, actual " + actual.toString)
  def assertFalse[T <: Boolean](actual: T, message: String = "") = if (actual) throw new AssertError(if (message != "") message else "expected false, actual " + actual.toString)
}
