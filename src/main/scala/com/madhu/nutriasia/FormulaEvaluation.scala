package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{expr, udf}



object FormulaEvaluation extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      (1, "(a+b)/d", 1, 20, 2, 3, 1),
      (2, "(c+b)*(a+e)", 0, 1, 2, 3, 4),
      (3, "a*(d+e+c)", 7, 10, 6, 2, 1)
    )
      .toDF("Id", "formula", "GSV_others", "Gross", "c", "d", "e")


    df
      .withColumn("result", expr("GSV_others + Gross"))
      .show()


  }
}
