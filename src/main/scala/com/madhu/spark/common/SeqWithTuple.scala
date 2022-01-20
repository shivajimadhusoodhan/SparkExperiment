package com.madhu.spark.common

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

object SeqWithTuple extends SparkSessionFactory {


  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "HumanicaMY", "IAM", 3),
      ("s2", "HumanicaTH", "IAM", 0),
      ("d3", "XPD8", "IAM", 0),
      ("f4", "Tesco Mobile", "IAM", 7),
      ("f9", "", "IAM", 7),
      ("d7", "ORACLE", "IAM", 4),
      ("d73", "PeopleDataVMS", "IAM", 4),
      ("f3", "6", "IAM", 9)).toDF("id", "iam_source", "source_system", "marks3")


    val iamSourceSystemFilterList = Seq(
      ("Tesco Mobile", "IAM"),
      ("HumanicaMY", "IAM"),
      ("HumanicaTH", "IAM"),
      ("XPD8", "IAM"),
      ("", "IAM")
    )

    val iamSourceFilterList = Seq("PeopleDataTRG", "PeopleDataVMS", "WFM")

    val iamSourceSystemFilters: Column = iamSourceSystemFilterList
      .map(tup => !($"iam_source" === tup._1 && $"source_system" === tup._2))
      .reduce(_ && _)

    val iamSourceFilters: Column = iamSourceFilterList
      .map(src => !($"iam_source" === src))
      .reduce(_ && _)

    df1.filter(iamSourceSystemFilters)
      .filter(iamSourceFilters).show()

  }
}
