package scodejam

import language.implicitConversions

object BigIntUtil {
  @inline def toBigInt(i: Int): BigInt = BigInt(i)
  @inline def toBigInt(i: Long): BigInt = BigInt(i)
  @inline def toBigInt(i: String): BigInt = BigInt(i)

  @inline implicit class IntExtensions(i: Int) {
    def bi: BigInt = BigIntUtil.toBigInt(i)
    def toBigInt: BigInt = BigIntUtil.toBigInt(i)
  }

  @inline implicit class LongExtensions(i: Long) {
    def bi: BigInt = BigIntUtil.toBigInt(i)
    def toBigInt: BigInt = BigIntUtil.toBigInt(i)
  }

  @inline implicit class StringExtensions(i: String) {
    def bi: BigInt = BigIntUtil.toBigInt(i)
    def toBigInt: BigInt = BigIntUtil.toBigInt(i)
  }
}

