package com.madhu.spark.headcount

import com.madhu.spark.common.SparkSessionFactory

import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.functions._

object CompareTables extends  SparkSessionFactory {

  def currentTime = System.currentTimeMillis()
  def deltaTime(t0: Long) = currentTime - t0

  def main (args : Array[String]): Unit = {
    try {

      import spark.implicits._

      val devDF = Seq(
        (1, "ram", "ayodhya", "2021-03"),
        (2, "lakhman", "", "2021-03"),
        (3, "bharath", "ayodhya", null),
        (4, "seetha", "janaka", "2021-03"),
        (5, "dfsa", "janaka", "2021-03")
      ).toDF("id","name", "city", "birthDate")

      val prodDF = Seq(
        (1, "ram", "ayodhya", "2021-03"),
        (2, "lakhman", "ayodhya", "2021-03"),
        (3, "bharath", "ayodhya", "2042-2"),
        (4, "seethamaa", "janaka", "2021-03")
      ).toDF("id","name", "city", "birthDate")

      val startTime = currentTime
      val priorityListCols = List("exact", "birthDate", "city", "name")
      val schema = devDF.schema

      val emptyDF = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schema).withColumn("remarks", lit(""))

      val (exactMatch, unmatchedDev, unmatchedProd) = getExactMatchedUnmatched(emptyDF,devDF, prodDF, priorityListCols)

      val finalDF = unmatchedDev.withColumn("remarks", lit("new records")).unionByName(exactMatch)

      val totalTime = deltaTime(startTime)
      println(s"Time taken:: ${totalTime}")


    }
  }

  def getExactMatchedUnmatched(exactDF: DataFrame, devDF: DataFrame, prodDF: DataFrame, excludeColumns: Seq[String]): (DataFrame, DataFrame, DataFrame) = {

    excludeColumns match {
      case Nil => (exactDF, devDF, prodDF)
      case head :: tail => {

        val excludeOneColumn = Seq(head)
        val joinColumns = devDF.columns.filter(!excludeOneColumn.contains(_))
        val joinClause = joinColumns.map((columnName: String) => devDF(columnName) <=> prodDF(columnName)).reduce((prev, curr) => prev && curr)

        val exactMatch = devDF.join(prodDF, joinClause).select(devDF("*"))
          .withColumn("remarks", when(lit(head.equals("exact")), lit("Exact Matching")) otherwise (lit(s"mismatch in ${head}")))
          .unionAll(exactDF)
          .cache

        // exactMatch.show()
        val unmatchedDevDF = devDF.join(prodDF, joinClause, "left_anti").cache()
        val unmatchedProdDF = prodDF.join(exactMatch, joinClause, "left_anti").cache()

        getExactMatchedUnmatched(exactMatch, unmatchedDevDF, unmatchedProdDF, tail)

      }
    }

  }

}
