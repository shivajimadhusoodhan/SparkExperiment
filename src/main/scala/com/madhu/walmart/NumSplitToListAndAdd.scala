package com.madhu.walmart

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.udf

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object NumSplitToListAndAdd extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      (2341),
      (341),
      (15),
      (15314),
      (2645),
      (2652),
      (65)).toDF( "salary")

    val getNumList =  (num: Int) => {
      var temp = num
      var numList = new ListBuffer[Int]()

      while(temp > 0) {
        val digit = temp%10
        numList += digit
        temp = temp/10
      }
      numList
    }

    val numListUdf = udf(getNumList)


    val sumOfArray = (nums: Array[Int]) => nums.foldLeft(0)(_ + _)
    val getSumUdf = udf(sumOfArray)
    val result = df
      .withColumn("numbers", numListUdf($"salary"))
      .withColumn("total", getSumUdf($"numbers"))

      result.show()

    result.printSchema()

  }

}
