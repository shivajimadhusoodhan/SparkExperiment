package com.madhu.spark.headcount

import com.madhu.spark.common.SparkSessionFactory
import com.madhu.spark.headcount.EffectiveDateUpdate.spark

object GetLatestRecords  extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val clgInfoDF = Seq(
      ("XYZ", "2021-01-01", "")
    ).toDF("Id", "EffectiveStartDate", "EffectiveEndDate")
  }

}
