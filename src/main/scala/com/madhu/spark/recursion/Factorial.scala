package com.madhu.spark.recursion

import com.madhu.spark.recursion.ReverseString.printReverse

object Factorial {

  def main(args: Array[String]): Unit = {
    val num = 9
    println(num)
    println(getFact(num))

  }

  def getFact(num: Int): Int = {
    if(num == 0)
      1
    else
      num * getFact(num-1)
  }
}
