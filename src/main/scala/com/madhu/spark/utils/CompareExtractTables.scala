package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import com.madhu.spark.utils.Timeformat.spark
import org.apache.spark.sql.functions.lit

object CompareExtractTables extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._


    val deltaDF = Seq(
      (1, "G1", "G-A"),
      (2, "G2", "G-B"),
      (3, "G3", "G-C"),
      (4, "G4", "G-D")
    ).toDF("id","name", "code")

    val azureDF = Seq(
      (1, "G1", "G-A", "A", "N"),
      (2, "G2", "G-Y", "A", "N"),
      (3, "G3", "G-C", "A", "N"),
      (5, "G5", "G-E", "A", "N"),
      (6, "G6", "G-F", "A", "N")
    ).toDF("id","name", "code", "status", "is_notified")

    val joinColumns = List("id","name", "code")

    val joinClause = joinColumns.map((columnName: String) => deltaDF(columnName) <=> azureDF(columnName)).reduce((prev, curr) => prev && curr)
    //val joinClause1 = joinColumns.map((_:String) => deltaDF(_) <=> azureDF(_)).reduce((prev, curr) => prev && curr)

    val exactMatchers = deltaDF
      .join(azureDF, joinClause )
      .select(deltaDF("*"))
      .withColumn("is_notified", lit("Y"))
      .withColumn("status", lit("A"))

      exactMatchers.show()

    val unmatchedDeltaDF = deltaDF.join(azureDF, Seq("id"), "left_anti").cache()
      .withColumn("is_notified", lit("N"))
      .withColumn("status", lit("A"))

    val unmatchedAzureDF = azureDF.join(deltaDF, Seq("id"), "left_anti").cache()
      .withColumn("is_notified", lit("N"))
      .withColumn("status", lit("I"))

    println(" ======================================== ")
    unmatchedDeltaDF.show()
    println(" ======================================== ")

    unmatchedAzureDF.show()
  }

}
