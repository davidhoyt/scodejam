package scodejam.inputs

import scodejam.{CodeJamSettings, InputProcessor}

trait StandardInputProcessor extends InputProcessor {
  def determineExpectedNumberOfCases(iter: Iterator[String]): Int
  def determineNextCaseSize(iter: Iterator[String]): Int
  def extractNextCase(iter: Iterator[String]): Vector[String]
  def caseSizeIsCorrect(case_content: Vector[String]): Boolean

  override def process(settings: CodeJamSettings, iter: Iterator[String])(fn: => FnInputProcessor)(fnCallback: FnInputCallback): Unit = {
    val expected_number_of_cases = determineExpectedNumberOfCases(iter)

    var case_number: Long = 1L
    var error_count: Int = 0

    while(iter.hasNext && (settings.maxWrongCasesBeforeStopping <= 0 || error_count < settings.maxWrongCasesBeforeStopping)) {
      val expected_case_content_size = determineNextCaseSize(iter)
      val case_content = extractNextCase(iter)
      if (caseSizeIsCorrect(case_content)) {
        try {
          val result = fn(case_content)
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
      } else {
        settings.println("    - <ERROR>   Case #%d: Expected %d values, but only %d were provided".format(case_number, expected_case_content_size, case_content.size))
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