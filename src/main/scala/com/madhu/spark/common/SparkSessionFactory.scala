package com.madhu.spark.common

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

trait SparkSessionFactory {

  implicit val spark = getSparkSession()

   private def getSparkSession() = {
      SparkSession.builder()
    .appName("SparkExportHiveJob")
    .master("local")
    .getOrCreate()

   }

}
