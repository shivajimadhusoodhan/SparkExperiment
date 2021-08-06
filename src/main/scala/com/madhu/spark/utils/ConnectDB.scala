package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

object ConnectDB extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {

    val query = "select * from HIVE_ORACLE_TEST"

    val df = spark.read.format("jdbc")
      .option("driver", "oracle.jdbc.driver.OracleDriver")
      .option("url", "jdbc:oracle:thin:@//domain:1521")
      .option("query", query)
      .option("user", "qwert")
      .option("password", "1234")
      .load()

    df.show()
  }

}
