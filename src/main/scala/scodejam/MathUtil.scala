package scodejam

import language.implicitConversions

object MathUtil {
  @inline def min(values:Int*): Int = {
    if (values.length <= 0)
      throw new IllegalArgumentException("min() requires at least 1 value")

    var m = values(0)
    for(v <- values) {
      if (v < m) {
        m = v
      }
    }

    m
  }

  @inline def max(values:Int*): Int = {
    if (values.length <= 0)
      throw new IllegalArgumentException("min() requires at least 1 value")

    var m = values(0)
    for(v <- values) {
      if (v > m) {
        m = v
      }
    }

    m
  }
}

