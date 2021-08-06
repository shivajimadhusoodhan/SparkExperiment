package com.madhu.spark.futureexp

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object MultipleFuturesWithSpark extends App with SparkSessionFactory {

  import spark.implicits._

  // use this to determine the “delta time” below
  val startTime = currentTime

  // (a) create three futures
  val dfFut1 = Future {getDf1()}
  val dfFut2 = Future {getDf2()}

//  getDf1()
//  getDf2()


  // important for a short parallel demo: you need to keep
  // the jvm’s main thread alive
  sleep(5000)

  def sleep(time: Long): Unit = Thread.sleep(time)


  def getDf1() =  {
    val r = scala.util.Random
    val randomSleepTime = r.nextInt(3000)
    println(s"For df1, sleep time is $randomSleepTime")

    val df1 = Seq(
      ("1", "ram", 3),
      ("2", "sita", 4),
      ("3", "lakshman", 5),
      ("4", "hanuman", 6))
      .toDF("Id", "Actual_Name", "marks")
    df1.show
    //df1
  }

  def getDf2() =   {
    val r = scala.util.Random
    val randomSleepTime = r.nextInt(3000)
    println(s"For df2, sleep time is $randomSleepTime")

    val df2 = Seq(
      ("1", "ec", 30),
      ("2", "tc", 48),
      ("3", "it", 56),
      ("4", "bt", 65)).toDF("Id", "branch", "strength")
    df2.show
    //df2
  }

 Await.ready(dfFut1, Duration.Inf)
 Await.ready(dfFut2, Duration.Inf)

  def currentTime = System.currentTimeMillis()
  def deltaTime(t0: Long) = currentTime - t0

  val totalTime = deltaTime(startTime)
  println(totalTime)

}
