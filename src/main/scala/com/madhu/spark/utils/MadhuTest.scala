package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

import java.time.LocalDate
import org.apache.spark.sql.{Column, Dataset}
import org.apache.spark.sql.functions._

object MadhuTest extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    val arr = spark.read.orc("C:\\Users\\Downloads\\strun.orc")
    arr.show

  }

  def testmin(argss: Array[String]) = {
    println(argss(0))
  }

}
