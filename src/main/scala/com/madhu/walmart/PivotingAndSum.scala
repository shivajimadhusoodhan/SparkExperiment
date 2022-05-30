package com.madhu.walmart

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object PivotingAndSum extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      (1, "A", 41),
      (1, "B", 34),
      (1, "C", 15),
      (1, "D", 14),
      (2, "A", 45),
      (2, "B", 51),
      (2, "C", 15),
      (2, "D", 13),
      (3, "A", 54),
      (3, "B", 45),
      (3, "C", 21)
    ).toDF("id", "subject", "marks")

    val subjectList = df.select("subject").distinct().map(sub => sub(0).toString).collect.toSeq

    val convertToMap = (a: Int, b: Int) => Map(a -> b) ++ Map(a -> b)

    val getMapUdf = udf(convertToMap)

    val calculateSum = (a: Int, b:Int) => a + b
    val getSumUdf = udf(calculateSum)

    val result = df
      .groupBy("id")
      .pivot("subject")
      .sum("marks")
      .na.fill(0, subjectList)
      .withColumn("total", lit(0))
      .cache
     // .withColumn("subject_map", getMapUdf($"", $"")) // USE FOLD LEFT WITH SUBJECT LIST


    def getSum(df: DataFrame) = {
      subjectList.foldLeft(df)((tempDF, subject) =>
        tempDF.withColumn("total", getSumUdf($"total", col(subject)))
      )
      df
    }

    val finalDF = getSum(result)

    finalDF.show()


  }
}
