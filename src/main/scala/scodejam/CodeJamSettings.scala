package scodejam

trait CodeJamSettings {
  def sourceDir: String = StandardCodeJamSettings.codeJamSourceDir
  def inputsDir: String = "codejam/inputs/"
  def outputsDir: String = "codejam/outputs/"

  def validInputFileExtension: String = ".txt"
  def showProgressOutput: Boolean = true

  def print(output: => Any) = if (showProgressOutput) Console.print(output)
  def println(output: => Any) = if (showProgressOutput) Console.println(output)
}

object CodeJamSettings {
  def apply(showProgress: Boolean = true) = new CodeJamSettings {
    override val showProgressOutput: Boolean = showProgress
  }
}

object StandardCodeJamSettings extends CodeJamSettings {
  val codeJamSourceDir: String = "codejam/"
}
