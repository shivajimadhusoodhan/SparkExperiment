package com.madhu.spark.headcount

import com.madhu.spark.SiriTestcolumns.spark
import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.udf

import scala.::

object CheckSequence extends SparkSessionFactory {


  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val df1 = Seq(
      ("1", "4", 3),
      ("2", "3", 4),
      ("", "5", 5),
      ("1", "1", 6),
      ("2", "2", 7),
      ("3", "6", 4)
    ).toDF("id", "regd", "marks")

    val df2 = Seq(
      ("1","Tesco"),
      ("2","sws"),
      ("3","Tesco")
    ).toDF("id", "name")


    df1.join(df2, Seq("id"), "left_outer").show()
    df1.join(df2, Seq("id"), "left_outer")
      .filter($"name".isNull || $"name".isin("Tesco")).show()
  }


}
