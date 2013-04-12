package scodejam

import language.implicitConversions

object TupleUtil {
  @inline def toCharMap(t: (String, String)): Map[Char, Char] = {
    val (key, value) = t
    key.zip(value).toMap
  }

  @inline implicit class Tuple2Extensions(t: (String, String)) {
    def toCharMap: Map[Char, Char] = TupleUtil.toCharMap(t)
  }
}

