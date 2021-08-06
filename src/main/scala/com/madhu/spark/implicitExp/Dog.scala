package com.madhu.spark.implicitExp

object Dog extends  Animals {

  def main(args: Array[String]): Unit = {

    printMammal("dog")

  }

  def printMammal(name: String)(implicit isMammal: Boolean) = {
    println(isMammal)
    println(name)
  }

}
