package com.madhu.spark.futureexp

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object MultipleFuturesInfDuration  {


  def main(args: Array[String]): Unit = {

    val a = printRecursiverly("first")
    val b = printRecursiverly1("second")

    Await.result(a, Duration.Inf)
    Await.result(b, Duration.Inf)

  }

  def printRecursiverly(process: String) = Future {
    (1 to 10).foreach(num => {
      Thread.sleep(3000)
      println(s"${process} printing :: ${num}")
    })
  }

  def printRecursiverly1(process: String) = Future {
    (1 to 10).foreach(num => {
      Thread.sleep(4000)
      println(s"${process} printing :: ${num}")
    })
  }

}
