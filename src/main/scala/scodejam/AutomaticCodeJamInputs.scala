package scodejam

import scodejam.inputs.InputAsOneCasePerLine
import java.io.File
import scodejam.outputs.OutputAsOneCasePerLine
import scodejam.Utils._
import java.util.Calendar

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
    if (!inputs_dir.exists()) {
      inputs_dir.mkdirs()
      if (!inputs_dir.exists())
        throw new IllegalStateException("The inputs directory \"" + settings.inputsDir + "\" does not exist in the expected locaton: " + inputs_dir.getAbsolutePath)
    }

    val outputs_dir = new File(settings.outputsDir)
    if (!outputs_dir.exists()) {
      outputs_dir.mkdirs()
      if (!outputs_dir.exists())
        throw new IllegalStateException("The outputs directory \"" + settings.outputsDir + "\" does not exist in the expected locaton: " + outputs_dir.getAbsolutePath)
    }

    inputs_dir.listFiles().filter(f => f.isFile && f.getName.toLowerCase.endsWith(settings.validInputFileExtension)).foreach { input =>
      val input_name = input.getName
      val output_name = input_name.substring(0, input_name.length - settings.validInputFileExtension.length) + settings.validOutputFileExtension
      val output = new File(settings.outputsDir, output_name)

      outputProcessor.reset
      solveForFile(input, output, settings, inputProcessor, outputProcessor)
    }

    onComplete()
  }

  def doNothing(): Unit = {
  }

  def zipProject(): Unit = {
    val out_dir = new File("solutions/")
    if (!out_dir.exists())
      out_dir.mkdirs()
    zipProject("solutions/" + submitter + "-" + problemName + "-google-codejam-" + (Calendar.getInstance().get(Calendar.YEAR)) + ".zip")
  }

  def zipProject(out: String, directory: String = "."): Unit = {
    Utils.zipProject(out, directory, Set("solutions/", "codejam/inputs/", "codejam/outputs/", ".git/", ".idea/", "target/"), Set("README.md","scodejam.iml", ".gitignore"))
  }

  def onComplete(): Unit = doNothing()

  def submitter: String
  def problemName: String

  def solveForCase(input: Vector[String]): String
}
