package scodejam

import scodejam.inputs.InputAsOneCasePerLine
import java.io.File

trait AutomaticCodeJamInputs extends ScalaScript {
  implicit def settings: CodeJamSettings = StandardCodeJamSettings
  implicit def inputProcessor: InputProcessor = InputAsOneCasePerLine

  override def run: Unit = run(settings, inputProcessor)

  private[this] def solveForFile(input: File, settings: CodeJamSettings, processor: InputProcessor) = {
    settings.println("  - " + input.getName)
    processor.process(settings, io.Source.fromFile(input).getLines(), solveForCase)
  }

  private[this] def run(implicit settings: CodeJamSettings, processor: InputProcessor): Unit = {
    val inputs_dir = new File(settings.inputsDir)
    if (!inputs_dir.exists())
      throw new IllegalStateException("The inputs directory \"" + settings.inputsDir + "\" does not exist in the expected locaton: " + inputs_dir.getAbsolutePath)

    val outputs_dir = new File(settings.outputsDir)
    if (!outputs_dir.exists())
      throw new IllegalStateException("The outputs directory \"" + settings.outputsDir + "\" does not exist in the expected locaton: " + outputs_dir.getAbsolutePath)

    inputs_dir.listFiles().filter(f => f.isFile && f.getName.toLowerCase.endsWith(settings.validInputFileExtension)).foreach { f =>
      solveForFile(f, settings, processor)
    }
  }

  def solveForCase(input: Vector[String]): String
}
