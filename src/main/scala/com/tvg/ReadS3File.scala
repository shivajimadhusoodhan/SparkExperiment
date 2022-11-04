package com.tvg

import org.apache.spark.sql.SparkSession

object ReadS3File {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
        .appName("SparkExportHiveJob")
        .master("local")
        .getOrCreate()

    val df = spark.read.csv("s3a://dev-hubs-temp/source/CAMPAIGN_EVENT_20120421.csv")

    df.show(3)

  }
}
