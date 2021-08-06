package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

object CSVanalysis extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {

    val filePath = "C:\\Users\\Downloads\\fact_dept_sales_pounds_by_sub_dept.csv"
    val calcFilePath = "C:\\Users\\Downloads\\wce_workload_element_values_calculated.csv"

    val deptPoundDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", true)
      .csv(filePath)

    val calcFilePathDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", true)
      .csv(calcFilePath)

    val finalDF = calcFilePathDF


    finalDF.show()

  }

}
