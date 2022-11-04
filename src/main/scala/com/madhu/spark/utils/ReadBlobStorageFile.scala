package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

object ReadBlobStorageFile extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    //spark.conf.set(s"")


    val df = spark.read
      .format("com.databricks.spark.csv")
      .option("escape", "\"")
      .option("quote", "\"")
      .option("encoding", "UTF-8")
      .option("header", "true")
      .csv("wasbs://dev-cdd-tap-extracts@devcddreportingstorage.blob.core.windows.net/dimensionsExtracts/grades_p10.csv")

    df.show()
  }

}
