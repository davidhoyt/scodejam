package scodejam

import language.implicitConversions
import scodejam.MathUtil._

object ArrayUtil {
  def print2DimensionalArray[T](arr2d: Array[Array[T]]) = {
    val max_digit_length = arr2d.foldLeft(1) { (a,arr) =>
      max(a, arr.map(_.toString.length).max)
    }
    val formatting = "%" + max_digit_length + "d"
    for(arr <- arr2d) {
      for(arr_value <- arr) {
        print(formatting format arr_value)
        print(" ")
      }
      println
    }
  }
}

