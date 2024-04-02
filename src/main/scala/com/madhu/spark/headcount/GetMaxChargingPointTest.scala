package com.madhu.spark.headcount

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

object GetMaxChargingPointTest  extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val clgInfoDF = Seq(
      ("1332323", "AN02323", "2021-01-01", "13:30:00", "2021-01-01", "14:30:00"),
      ("1332323", "AN02323", "2021-01-01", "14:31:00", "2021-01-01", "16:11:00"),

      ("1332324", "AN02324", "2021-02-01", "13:30:00", "2021-02-01", "19:30:00"),
      ("1332325", "AN02324", "2021-02-02", "20:30:00", "2021-02-02", "22:30:00")

    ).toDF("event_id", "cpid", "startdate", "starttime", "enddate", "endtime")


    val trf_data = clgInfoDF
      .withColumn("start_timestamp_str", concat(col("StartDate"), lit(" "), col("starttime")))
      .withColumn("end_timestamp_str", concat(col("enddate"), lit(" "), col("endtime")))
      .withColumn("start_timestamp", unix_timestamp(col("start_timestamp_str"), "yyyy-MM-dd HH:mm:ss"))
      .withColumn("end_timestamp", unix_timestamp(col("end_timestamp_str"), "yyyy-MM-dd HH:mm:ss"))
      .withColumn("duration_charging", (col("end_timestamp") - col("start_timestamp")) / 3600 )
      .withColumn("duration_charging", round(col("duration_charging"), 2))
      .drop("event_id", "startdate", "enddate", "starttime", "endtime", "start_timestamp_str", "end_timestamp_str")
      .groupBy("cpid")
      .agg(avg("duration_charging").alias("avg_duration"),
           max("duration_charging").alias("max_duration"))
    trf_data.show(false)

    trf_data.printSchema()

  }

}
