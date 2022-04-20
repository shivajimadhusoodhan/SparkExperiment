package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{Decimal, DecimalType}

object DivideByZeroExp  extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "4", 3, 3),
      ("s2", "3", 4, 0),
      ("d3", "5", 0, 0),
      ("f4", "1", 0, 7),
      ("", "2", 7, 4),
      (null, "6", 4, 9)).toDF("dept", "Id", "marks2", "marks3")

    df1
      .withColumn("percentage", lit(100.0)/($"marks2" * $"marks3"))
      .withColumn("percentage", $"percentage".cast(DecimalType(25, 2)))
      //.withColumn("date", regexp_replace($"dept", null, "ddd"))
      .na.fill(0.0, Seq("percentage"))

      .withColumn("dept", coalesce($"dept", lit("djdjd")))
     // .filter($"percentage".isNull)
      .show
  }

}
