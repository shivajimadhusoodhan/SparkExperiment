package com.madhu.spark.recursion


object SumOfList {

  def main(args: Array[String]): Unit = {
    val numList = List(3, 4 ,6, 3, 7)

    val (a, b) = numList.span(_ < 6)

    a.foreach(print)
    println()
    b.foreach(print)
    println()

    numList.foreach(print)
    println()
    println(numList.sum)
    println(s"By iterating :: ${getSumByIterating(numList)}")
    println(s"By tail rec:: ${getSumByTailRecursion(numList)}")
    println(s"By foldLeft :: ${getSumByFolding(numList)}")

  }

  def getSumByIterating(numList: List[Int]): Int = {
    var sum = 0
    for(i <- numList) {
      sum = sum + i
    }
    sum
  }

  def getSumByTailRecursion(numList: List[Int]): Int = {
    numList match {
      case x :: xs => val sum = getSumByIterating(xs)
                      sum + x
      case Nil => 0
    }
  }

  def getSumByFolding(numList: List[Int]): Int = {

    //numList.foldLeft(0)((temp, num) => temp + num)
    numList.foldLeft(0)(_ + _)
  }
}
