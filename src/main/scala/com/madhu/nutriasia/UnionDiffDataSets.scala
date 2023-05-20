package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory

object UnionDiffDataSets extends SparkSessionFactory {


  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "HumanicaMY", "IAM", 3),
      ("a2", "HumanicaTH", "IAM", 0),
      ("a3", "XPD8", "IAM", 0),
      ("a4", "Tesco Mobile", "IAM", 7)).toDF("id", "iam_source", "source_system", "maths")

    val df2 = Seq(
      ("a1", "HumanicaMY", "IAM", 23),
      ("a2", "HumanicaTH", "IAM", 23),
      ("a3", "XPD8", "IAM", 20),
      ("a4", "Tesco Mobile", "IAM", 37)).toDF("id", "iam_source", "source_system", "science")

    val df3 = Seq(
      ("a1", "HumanicaMY", "IAM"),
      ("a2", "HumanicaTH", "IAM"),
      ("a3", "XPD8", "IAM"),
      ("a4", "Tesco Mobile", "IAM")).toDF("id", "source", "source_system")
      .withColumnRenamed("source", "iam_source")

    val finalDF = df1
      .unionByName(df2, true)
      .unionByName(df3, true)

    finalDF.show()

  }
}
