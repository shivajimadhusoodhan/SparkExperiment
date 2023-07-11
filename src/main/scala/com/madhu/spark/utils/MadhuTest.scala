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
    ("XYZ", "2021-01-01", "2021-04-02T21:11:56.207Z", "xy"),
    ("XYZ", "2021-02-01", "2021-01-02T11:13:56.207Z", "xy")
    ).toDF("customer", "dateval", "timeval", "ship_to")

    val newDF = clgInfoDF
      .withColumn("dateval", to_date($"dateval"))
      .withColumn("timeval", to_timestamp($"timeval"))

    clgInfoDF.show()

    var final_df = clgInfoDF

//    final_df = final_df.withColumn("new_customer", clgInfoDF("ship_to"))
//    final_df = final_df.withColumn("new_customer_0", clgInfoDF("customer"))

    final_df = final_df.select(col("ship_to").as("customer"),
      col("ship_to").as("ship_to"), col("customer").as("customer_0"))
    final_df.show()
  }

}
