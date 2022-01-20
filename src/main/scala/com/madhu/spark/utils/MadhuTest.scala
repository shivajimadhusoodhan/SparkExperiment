package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import com.madhu.spark.headcount.EffectiveDateUpdate.spark

import java.time.LocalDate
import org.apache.spark.sql.{Column, Dataset}
import org.apache.spark.sql.functions._

object MadhuTest {//extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    // add ClgInfo column values here
    val clgInfoDF = Seq(
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z"),
    ("XYZ", "2021-02-01", "2021-01-02T11:13:56.207Z")
    ).toDF("id", "dateval", "timeval")

    val newDF = clgInfoDF
      .withColumn("dateval", to_date($"dateval"))
      .withColumn("timeval", to_timestamp($"timeval"))

    newDF.orderBy("timeval").show(false)
    newDF.printSchema()

    val str = "ab$,$jdsfh$,$djhf"
    str.split("\\$,\\$").foreach(println)
  }

}
