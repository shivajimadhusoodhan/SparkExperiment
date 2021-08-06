package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

object ReplaceColumnNames  extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "4", 3, 3),
      ("s2", "3", 4, 0),
      ("d3", "5", 0, 0),
      ("f4", "1", 0, 7),
      ("g5", "2", 7, 4),
      ("h6", "6", 4, 9)).toDF("dept", "Id", "marks2", "marks3")

    val df2 = df1
      .groupBy("dept", "Id")
      .sum("marks2")

     df2
      .show()
  }

}
