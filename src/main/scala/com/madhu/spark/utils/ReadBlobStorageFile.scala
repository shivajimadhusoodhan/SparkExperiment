package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

object ReadBlobStorageFile extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    spark.conf.set(s"fs.azure.account.key.devcddreportingstorage.blob.core.windows.net", "tJDFoSx+jWQ6CwreKGKMtCEOhaEFCjCwo9vBSmd9i7maN0KX/KXBLbqb4k0JirynwlWY3ml/Wri0VoDqDXqPxw==")


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
