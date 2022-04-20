package com.madhu.interview.meesho

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object FirstAssignment extends  SparkSessionFactory{


  val convertToSquare = (value: Double) => value * value

  val squareUdf: UserDefinedFunction = udf(convertToSquare)

  def transformDF(inputDF: DataFrame, udfMap: Map[String, UserDefinedFunction]) = {

    val listm = List("df", "ddf")

    udfMap.foldLeft(inputDF:DataFrame) {
      case (tempDf, (columnName, udf)) => tempDf.withColumn(columnName, udf(col(columnName)))
    }
    inputDF
  }

  def main(args: Array[String]): Unit = {

  }

}
