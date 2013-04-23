package scodejam.inputs

import scodejam.{CodeJamSettings, InputProcessor}

class InputWithIterator[T <: Iterator[String]] extends InputProcessor {
  def determineExpectedNumberOfCases(iter: Iterator[String]): Int = iter.next().toInt

  override def process(settings: CodeJamSettings, iter: Iterator[String])(fn: => FnInputProcessor)(fnCallback: FnInputCallback): Unit = {
    val expected_number_of_cases = determineExpectedNumberOfCases(iter)

    var case_number: Long = 1L
    var error_count: Int = 0

    while(iter.hasNext && (settings.maxWrongCasesBeforeStopping <= 0 || error_count < settings.maxWrongCasesBeforeStopping)) {
      try {
        val result = fn(iter)
        settings.println("    - <SUCCESS> Case #%d: %s".format(case_number, result))
        fnCallback(result)
      } catch {
        case t: Throwable => {
          error_count += 1
          settings.println("    - <ERROR> <EXCEPTION> %s".format(t.toString))
          fnCallback("")
        }
      } finally {
        case_number += 1L
      }
    }

    case_number = if (case_number > 0) case_number - 1 else 0

    if (case_number == expected_number_of_cases) {
      settings.println("    - ** Completed all cases **")
    } else {
      if (error_count < settings.maxWrongCasesBeforeStopping) {
        settings.println("    - ** <ERROR> Expected %d cases, but only %d were provided or evaluated **".format(expected_number_of_cases, case_number))
      } else {
        settings.println("    - ** <ERROR> Maximum # of errors (%d) was reached. Aborting further processing. **".format(settings.maxWrongCasesBeforeStopping))
      }
    }
  }
}
object InputWithIterator extends InputWithIterator
