package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object Wordcount extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._


    val deltaDF = Seq(
      (1, "G1 I LOVE", "G-A"),
      (2, "G2 I love love You", "G-B"),
      (1, "G3 i LoVE you", "G-C"),
      (2, "G3 i LoVE you", "G-C"),
      (3, "G4 i lOVE you", "G-D")
    ).toDF("id","name", "code")

    import spark.implicits._

    val wordFilterUdf = udf((arr: Array[String])=> {
      val filtered = arr.filter(_.toLowerCase().equals("love"))
        filtered.size
    })

   val df: DataFrame = deltaDF
     /*.withColumn("textWords", split($"name", " "))
     .withColumn("textWords", explode_outer($"textWords"))
     .filter(lower($"textWords") === "love")
     .groupBy("id").agg(count($"textWords").as("countdd"))*/
//     .withColumn("textWords", split($"name", " "))
//     .withColumn("count", wordFilterUdf($"textWords"))
     //.flatMap(split("name", " "))
     .filter($"name".rlike("(?i)^*love*"))

    df.show() //.select("id", "count").show()













  }

}
