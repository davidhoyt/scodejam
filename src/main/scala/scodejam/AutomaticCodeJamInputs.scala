package scodejam

import scodejam.inputs.InputAsOneCasePerLine
import java.io.File
import scodejam.outputs.OutputAsOneCasePerLine
import scodejam.Utils._

trait AutomaticCodeJamInputs extends ScalaScript {
  implicit def settings: CodeJamSettings = StandardCodeJamSettings
  implicit def inputProcessor: InputProcessor = InputAsOneCasePerLine
  implicit def outputProcessor: OutputProcessor = OutputAsOneCasePerLine

  override def run: Unit = run(settings, inputProcessor, outputProcessor)

  private[this] def solveForFile(input: File, output: File, settings: CodeJamSettings, inputProcessor: InputProcessor, outputProcessor: OutputProcessor) = {
    settings.println("  - " + input.getName)

    printToFile(output) { writer =>
      inputProcessor.process(settings, io.Source.fromFile(input).getLines())(solveForCase) { result =>
        outputProcessor.process(writer)(result)
      }
    }
  }

  private[this] def run(implicit settings: CodeJamSettings, inputProcessor: InputProcessor, outputProcessor: OutputProcessor): Unit = {
    val inputs_dir = new File(settings.inputsDir)
    if (!inputs_dir.exists())
      throw new IllegalStateException("The inputs directory \"" + settings.inputsDir + "\" does not exist in the expected locaton: " + inputs_dir.getAbsolutePath)

    val outputs_dir = new File(settings.outputsDir)
    if (!outputs_dir.exists())
      throw new IllegalStateException("The outputs directory \"" + settings.outputsDir + "\" does not exist in the expected locaton: " + outputs_dir.getAbsolutePath)

    inputs_dir.listFiles().filter(f => f.isFile && f.getName.toLowerCase.endsWith(settings.validInputFileExtension)).foreach { input =>
      val output = new File(settings.outputsDir, input.getName)
      outputProcessor.reset
      solveForFile(input, output, settings, inputProcessor, outputProcessor)
    }

    onComplete()
  }

  def solveForCase(input: Vector[String]): String

  def onComplete(): Unit
}
