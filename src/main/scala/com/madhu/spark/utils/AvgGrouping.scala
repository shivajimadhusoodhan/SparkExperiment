package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

object AvgGrouping  extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("1", "a", 3),
      ("1", "a", 4),
      ("1", "a", 5),
      ("2", "b", 6),
      ("2", "b", 7),
      ("3", "c", 4)).toDF("Id", "Actual_Name", "marks")
        /*.groupBy("Id", "Actual_Name")
        .avg("marks")*/
     // .withColumn("sum", sumOfSlots(Seq(col("marks1"), col("marks2"))))

    val ab =df1
      .filter(col("marks").equalTo(6))
      .select("marks").as[String]
      .collect.head.toInt

    println(ab)



  }

}
