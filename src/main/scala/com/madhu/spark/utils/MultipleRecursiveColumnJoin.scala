package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.{Column, DataFrame, SaveMode}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, DecimalType, IntegerType, MapType, StringType, StructField, StructType}

object MultipleRecursiveColumnJoin extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val wlDF = Seq(
      (1, "D1", "D2"),
      (2, "D1", "D3"),
      (3, "D3", "D2"),
      (4, "D5", ""),
      (5, "D6", "D5"),
      (6, "", "D5")
    ).toDF("id", "driver0", "driver1")

    val driverDF = Seq(
      ("D1", "101"),
      ("D2", "102"),
      ("D3", "103"),
      ("D4", "104"),
      ("D5", "105"),
      ("D6", "106")
    ).toDF("driver_code", "driver_value")

    val procDF = wlDF
      .withColumn("mapper", typedLit(Map[String, String]()))

    val finalColumns = procDF.columns.toSeq

    val joinColumnConditionDriver: Array[Column] = wlDF.columns
      .withFilter(_.startsWith("driver"))
      .map(col(_) === driverDF("driver_code"))

    def setAdditionalFields = udf {
      (mapper: Map[String, String], driverCode: String, driverValue: String) =>
        mapper + (driverCode -> driverValue)
    }

    val finalDF: DataFrame = joinColumnConditionDriver.zipWithIndex.toList.foldLeft(procDF)((tempDF, i) =>
      tempDF
        .join(driverDF, i._1, "left")
      .withColumn("mapper", when(tempDF(s"driver${i._2}").isNotNull && !(tempDF(s"driver${i._2}") === ""),
        setAdditionalFields($"mapper", tempDF(s"driver${i._2}"), driverDF("driver_value")))
        otherwise($"mapper"))
      .select(finalColumns.map(col): _*)

    )

    finalDF.show()
    //procDF.show()
  }

  def meth() = {
    import spark.implicits._

    val df = Seq((1, "madhu", "hr"), (2, "sindhu", "it"), (3, "rohit", "staff")).toDF("id", "name", "dept")
    df.write.partitionBy("dept", "id").saveAsTable("lab_pm_gold.test_transaction_history")

  }
}
