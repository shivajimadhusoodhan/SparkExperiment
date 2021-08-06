package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

object ExceptExperiment extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {

    import spark.implicits._
    val df1 = Seq(
      ("1", "4", 3),
      ("1", "3", 4),
      ("1", "5", 5),
      ("1", "1", 6),
      ("2", "2", 7),
      ("3", "6", 4)).toDF("id", "name", "marks")

    val df2 = Seq(
      ("1", "4", 3),
      ("1", "13", 4),
      ("1", "5", 5),
      ("1", "11", 6),
      ("2", "2", 7),
      ("3", "6", 4)).toDF("id1", "name1", "marks1")

    //df2.except(df1).show()

    df1.count() match {
      case 7L => println("7 rows")
      case _ => println("not sure rows")
    }
  }

}
