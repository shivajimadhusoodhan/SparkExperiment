package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{col, count, sum, trim}

object SAPQueryImplement extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    
    import spark.implicits._

    val df1 = Seq(
      ("m1", "101", 200, 2),
      ("m1", "101", 500, 5),
      ("m1", "101", 900, 9),
      ("m2", "101", 400, 2),
      ("m2", "101", 200, 1),
      ("m1", "102", 700, 2),
      ("m1", "102", 100, 1),
      ("m1", "102", 300, 3),
      ("m2", "102", 600, 3),
      ("m2", "102", 250, 1),
      ("m2", "102", 450, 2)
    ).toDF("material", "store", "sales", "quantity")

    df1.show()

    df1
      .groupBy("store", "material")
      .agg(sum("sales"), sum("quantity"))
      .orderBy("store", "material")
      .show()
  }
}
