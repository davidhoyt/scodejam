scodejam
========

Google Code Jam framework (not actual answers) for processing input files and producing output files. This code also provides the option to completely zip up the project for submission.

File Layout
-----------
All files under src/ are for the core input file processing and zipping and will be included in all code jam source zip files. These should ***not*** typically be edited directly. Your code belongs in codejam/codejam.scala. The directory structure is expected to be laid out like:

    codejam/ 
      codejam.scala
      inputs/ 
        A-small-practice.in
        ...
      outputs/
        A-small-practice.out
        ...

codejam.scala
-------------
This file located at `codejam/codejam.scala` is where you're expected to do your work. When you run the project, it will monitor this file looking for changes and when a change is detected, it will automatically re-compile and re-run available input files against it, providing a report with the results for each file. Here's an example solution for the "[Speaking in Tongues] [1]" problem:

    import scodejam._
    import scodejam.inputs._
    import scodejam.outputs._
    import scodejam.Utils._
    import scodejam.Assert._
    import scodejam.TupleUtil._
    import scodejam.StringUtil._
    
    class CodeJam extends AutomaticCodeJamInputs {
      override implicit val settings = CodeJamSettings(maxWrongCases = 1, showProgress = true)
      override implicit val inputProcessor = InputAsOneCasePerMultipleLines(linesPerCase = 1)
      override implicit val outputProcessor = OutputAsOneCasePerLine()

      override val submitter = "jdoe"    
      override val problemName = "speaking-in-tongues"
    
      override def onComplete(): Unit = zipProject()
    
      override def solveForCase(input: Vector[String]): String = {
        val char_map = ("ejpmyslckdxvnribtahwougfqz" -> "ourlangeismpbtdhwyxfkjvczq").toCharMap
    
        val first = input(0)
        first.map(c => char_map.getOrElse(c, c))
      }
    }

It's important the class be named `CodeJam` since that's explicitly looked for. It's best to extend `AutomaticCodeJamInputs` but is not required. If you do not, you will need to extend `ScalaScript` and implement the `run()` method.

The overridden implicits are entirely optional and by default the code will assume there is one case per line and call `solveForCase()` with a `Vector[String]` of size 1 with the single line containing the value to be considered.

One instance of `CodeJam` is created for each entire run and shared across multiple input files. Do not assume a new instance is provided for each input file.

`problemName` and `submitter` are used primarily for the zip file name that will include your source. If you do not want to create a zip file on each file, change:

    override def onComplete(): Unit = zipProject()

to

    override def onComplete(): Unit = doNothing()

or completely remove the `onComplete()` implementation.

  [1]: https://code.google.com/codejam/contest/1460488/dashboard

