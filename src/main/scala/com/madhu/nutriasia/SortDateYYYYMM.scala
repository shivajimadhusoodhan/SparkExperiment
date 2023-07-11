package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

object SortDateYYYYMM extends SparkSessionFactory {




  def main(args: Array[String]): Unit = {

    import spark.implicits._

    /*val df = Seq(
      ("202112"),
      ("202111"),
      ("202109"),
      ("202110"),
      ("202102"),
      ("202106"),
      ("202103")
    ).toDF("CALMONTH")*/

    spark.sql("set spark.sql.legacy.timeParserPolicy=LEGACY")

    val df = Seq(
      ("2019-07-01 12:01:19"),
      ("2019-06-24 12:01:19"),
      ("2019-11-16 12:01:19"),
      ("2019-11-16 12:01:19")
    ).toDF("input_timestamp")

    //Timestamp String to DateType
    df.withColumn("datetype",
      to_date(col("input_timestamp"), "yyyy/MM/dd"))
      .show(false)
  }

}
