package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{col, count, trim}
object GetMappingAnalysis extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val filePath = "/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/SalesToTrade.csv"
    val fileDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", true)
      .csv(filePath)
      .withColumn("Source ADSO", trim(col("Source ADSO")))
      .withColumn("Source Field", trim(col("Source Field")))
      .withColumn("Target Field", trim(col("Target Field")))
      .cache()

    fileDF.show()

    val sourceWiseColumns = fileDF
      .groupBy("Source ADSO")
      .agg(count($"Source Field").as("sourceFieldCount"))

    sourceWiseColumns.show()

    val targetFieldCountDF = fileDF.groupBy("Target Field").count()
    targetFieldCountDF.show()

    val targetFieldDescendingDF = fileDF
      .join(targetFieldCountDF, Seq("Target Field"))
      .orderBy($"count".desc, $"Target Field")
      .select("Source ADSO", "Source Field", "Target Field")

    targetFieldDescendingDF.show()

    targetFieldDescendingDF.write.csv("/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/SalesToTradeSorted.csv")
  }

}
