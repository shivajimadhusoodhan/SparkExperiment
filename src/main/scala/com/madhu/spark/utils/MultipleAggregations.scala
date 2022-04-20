package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object MultipleAggregations extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._


    val movieDF = Seq(
      (1, "G1", 2),
      (2, "G2", 4),
      (3, "G3", 2),
      (2, "G3", 3),
      (3, "G4", 3)
    ).toDF("id","name", "rating")

    import spark.implicits._

   val df = movieDF.groupBy("id")
     .agg(avg("rating"),
          count("name"))

    df.show()













  }

}
