package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

object PivotTest  extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "MATHS", 3, 3),
      ("a1", "SCIENCE", 6, 3),
      ("a1", "ENGLISH", 9, 3),
      ("a2", "SCIENCE", 4, 0),
      ("a2", "MATHS", 6, 0),
      ("a3", "ENGLISH", 2, 5),
      ("a3", "SCIENCE", 2, 5)).toDF( "Id", "subject", "marks", "marks3")
//
    val df2 = df1
        .groupBy("id", "marks3")
        .pivot("subject")
        .sum("marks")

    val kvCols = Seq("ENGLISH", "MATHS", "SCIENCE").flatMap(c => Seq(lit(c), col(c).cast("String")))

    val convertToString = udf{
      (subMap: Map[String, String]) => subMap.map(data => s"${data._1}:${data._2}").mkString(",")
    }



    df2
        .na.fill(0, Seq("ENGLISH", "MATHS", "SCIENCE"))
      .withColumn("ConvertedCols", map(kvCols: _*))
      .withColumn("allMap", convertToString(col("ConvertedCols")))
      //.printSchema()
      .show(false)
  }

}
