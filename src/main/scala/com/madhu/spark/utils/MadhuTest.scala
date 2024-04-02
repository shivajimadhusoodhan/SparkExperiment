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
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "", 2.33),
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "     ", 2.33),
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "     xy003", 2.33),
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "   x  y005  ", 2.33),
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "xy005", 2.33),
    ("XYZ", "2021-02-01", "2021-01-02T11:13:56.207Z", "xz006   ", 2.33)
    ).toDF("customer", "dateval", "timeval", "ship_to", "amount")

    val newDF = clgInfoDF
      .withColumn("dateval", to_date($"dateval"))
      .withColumn("timeval", to_timestamp($"timeval"))
//      .withColumn("ship_to", trim(col("ship_to")))
//      .withColumn("amount", trim(col("amount")))

    clgInfoDF.show()

    val final_df = newDF
//      .filter(col("ship_to").equalTo(""))
      .withColumn("newcol", when(trim(col("ship_to")).equalTo(""), lit("empty maga")) otherwise lit("great"))


    final_df.show()
  }

}
