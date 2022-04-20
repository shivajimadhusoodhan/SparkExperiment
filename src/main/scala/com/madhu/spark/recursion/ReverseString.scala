package com.madhu.spark.recursion

object ReverseString {

  def main(args: Array[String]): Unit = {
    val str = "SCALA"
    println(str)
    println()
    printReverse(str.toList)

  }

  def printReverse(chars: List[Char]): Unit = {
    chars match {
      case x :: xs => printReverse(xs)
                      print(x)
      case Nil => print("")
    }
  }
}
