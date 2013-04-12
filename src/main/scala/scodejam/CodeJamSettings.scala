package scodejam

trait CodeJamSettings {
  def sourceDir: String = StandardCodeJamSettings.codeJamSourceDir
  def inputsDir: String = "codejam/inputs/"
  def outputsDir: String = "codejam/outputs/"

  def validOutputFileExtension: String = ".out"

  def validInputFileExtension: String = ".in"
  def showProgressOutput: Boolean = true

  def maxWrongCasesBeforeStopping: Int = 3

  def codeSubmitter: String = "<unknown submitter>"

  def print(output: => Any) = if (showProgressOutput) Console.print(output)
  def println(output: => Any) = if (showProgressOutput) Console.println(output)
}

object CodeJamSettings {
  def apply(submitter: String = "<unknown submitter>", maxWrongCases: Int = 3, showProgress: Boolean = true) = new CodeJamSettings {
    override val codeSubmitter = submitter
    override val showProgressOutput: Boolean = showProgress
    override val maxWrongCasesBeforeStopping: Int = maxWrongCases
  }
}

object StandardCodeJamSettings extends CodeJamSettings {
  val codeJamSourceDir: String = "codejam/"
}
