package scodejam.inputs

import scodejam.{CodeJamSettings, InputProcessor}

class InputAsOneCasePerMultipleLines(private val linesPerCase: Int) extends InputProcessor {
  if (linesPerCase < 1)
    throw new IllegalArgumentException("There must be at least 1 line per case")

  override def process(settings: CodeJamSettings, iter: Iterator[String], fn: => FnInputProcessor): Unit = {
    val expected_number_of_cases = iter.next().toInt

    var case_number: Long = 1L

    while(iter.hasNext) {
      val case_content = iter.take(linesPerCase).toVector
      if (case_content.size == linesPerCase) {
        try {
          val result = fn(case_content)
          settings.println("    - <SUCCESS> Case #%d: %s".format(case_number, result))
        } catch {
          case t: Throwable => {
            settings.println("    - <ERROR> <EXCEPTION> %s".format(t.toString))
          }
        } finally {
          case_number += 1L
        }
      } else {
        settings.println("    - <ERROR>   Case #%d: Expected %d values, but only %d were provided".format(case_number, linesPerCase, case_content.size))
        case_number += 1L
      }
    }

    case_number = if (case_number > 0) case_number - 1 else 0

    if (case_number == expected_number_of_cases) {
      settings.println("    - ** Completed all cases **")
    } else {
      settings.println("    - ** <ERROR> Expected %d cases, but only %d were provided **".format(expected_number_of_cases, case_number))
    }
  }
}

object InputAsOneCasePerMultipleLines {
  def apply(linesPerCase: Int = 1): InputProcessor = new InputAsOneCasePerMultipleLines(linesPerCase)
}
