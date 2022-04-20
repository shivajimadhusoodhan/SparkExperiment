package com.madhu.spark.common

object YieldExperiment {

  def main(args: Array[String]): Unit = {
    //val sum = 0
    val result: Seq[Int] = for(i <- 1 to 10) yield i*2

    val total = (1 to 10).foldLeft(5)((sum, i) => sum + i)

    result.foreach(println)

    println(total)
    println(result.forall(_ % 2 == 0))
  }


}
