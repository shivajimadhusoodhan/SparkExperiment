package com.madhu.spark.headcount

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row}

object GetMismatchColumns extends  SparkSessionFactory {

  def currentTime = System.currentTimeMillis()
  def deltaTime(t0: Long) = currentTime - t0

  def main (args : Array[String]): Unit = {
    try {

      import spark.implicits._

      val devDF = Seq(
        (1, "ram", "ayodhya", "2021-03"),
        (2, "lakhman", "", "2021-03"),
        (3, "bharath", "ayodhya", null),
        (4, "seetha", "janaka", "2021-03")
      ).toDF("id","name", "city", "birthDate")

      val prodDF = Seq(
        (1, "ram", "ayodhya", "2021-03"),
        (2, "lakhman", "ayodhya", "2021-03"),
        (3, "bharath", "ayodhya", "2042-2"),
        (4, "seethamaa", "janak", "2021-03")
      ).toDF("id","name", "city", "birthDate")


      val resultDF = devDF.join(prodDF, Seq("id"))
        .withColumn("remarks", lit(":"))

//      resultDF.printSchema()
//      resultDF.show()
//
//      resultDF.select(devDF("name"), prodDF("birthDate")).show()
      val priorityListCols = List("birthDate", "city", "name")
      val compareDF = getMismatchColumnsRemarks(devDF, prodDF, resultDF, priorityListCols)

      compareDF.show(false)
      /*val startTime = currentTime
      val priorityListCols = List("exact", "birthDate", "city", "name")
      val schema = devDF.schema

      val emptyDF = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schema).withColumn("remarks", lit(""))

      val (exactMatch, unmatchedDev, unmatchedProd) = getExactMatchedUnmatched(emptyDF,devDF, prodDF, priorityListCols)

      val finalDF = unmatchedDev.withColumn("remarks", lit("new records")).unionByName(exactMatch)

      val totalTime = deltaTime(startTime)
      println(s"Time taken:: ${totalTime}")*/


    }
  }

  def getMismatchColumnsRemarks(devDF: DataFrame, prodDF: DataFrame, resultDF: DataFrame, compareColumns: Seq[String]):  DataFrame = {
    import spark.implicits._
    compareColumns.foldLeft(resultDF)((tempDF, col) => {
      tempDF.withColumn("remarks", when(devDF(col) <=> prodDF(col), $"remarks") otherwise concat($"remarks", lit(s":${col}")))
    })
  }

}
