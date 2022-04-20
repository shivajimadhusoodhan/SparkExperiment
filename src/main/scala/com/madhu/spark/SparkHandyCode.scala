package com.madhu.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.{col, not, udf}
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkHandyCode {

  def main(args: Array[String]): Unit = {

    //SPARK INITIALISE

    val conf = new SparkConf
    conf.set("spark.executor.memory", "4g")
    conf.set("spark.driver.memory", "4g")
    conf.set("spark.cores.max", "2")

    val spark =  SparkSession.builder()
      .appName("SparkExportHiveJob")
      .master("local")
      .config(conf)
      .getOrCreate()

    // IMPORT SPARK IMPLICITS
    import spark.implicits._

    // READING CSV
    val csvDF = spark.read.format("csv").option("header", "true").option("escape", "\"").csv("filePath")

    // READ TABLE
    //val tableDF =  spark.read.jdbc("getConnectionUrl", "tableName", java.util.Properties)

    // READ PARQUET
    val parquetDF =  spark.read.parquet("filePath")

    // CREATE DF WITH SEQ
    val columnList = List("id", "name")
    val seqDF = Seq((1, "ram"), (2, "lakshman")).toDF(columnList: _*)

    //CREATE DF WITH RANGE
    val rangeDF = spark.range(1, 100).toDF("numbers")

/*

    // TYPES OF JOIN
    empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"), "leftsemi")
    empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"inner")
    empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"leftouter")
    empDF.alias("emp1").join(empDF.alias("emp2"),
      col("emp1.superior_emp_id") == col("emp2.emp_id"),"inner")
     .select(col("emp1.emp_id"),col("emp1.name"))

    empDF.join(deptDF,empDF("emp_dept_id") === deptDF("dept_id"), "left") // you can use anti, leftanti, left_anti

    empDF.join(deptDF,empDF("emp_dept_id") ===   deptDF("dept_id"),"outer") //you can use either outer, full, fullouter

    empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"right") //you can use either rightouter

    //FILTER

    val someList = List("1", "2")
    df.filter(not($"c4".isin(someList: _*)))
    df.filter($"column".rlike("(?i)^*spark*$")) //.rlike("^[0-9]*$")  .rlike("(?i)^*rose$"))
    df.filter(col("name").startsWith("James"))
    df.filter( col("name").startsWith("James") === false)


    // WITH COLUMN
    df.withColumn("percentage", $"percentage".cast(DecimalType(25, 2)))
    df.withColumn("dateval", to_date($"dateval"))
    df.withColumn("timeval", to_timestamp($"timeval"))


    // TO FILL NULL WITH 0
     df.na.fill(0.0, Seq("percentage"))

    // IF DEPT COLUMN IS NULL, THEN REPLACE WITH DEFAULT VALUE
    df.withColumn("dept", coalesce($"dept", lit("djdjd")))


    // WRITE DF
    df.write
      .mode(SaveMode.Append)
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("encoding", "UTF-8")
      .option("quoteAll", true)
      .csv("filepath")


    // CREATE A UDF
    val convertCase =  (str:String) => {
      if(str.isEmpty || str == null) {
        ""
      } else {
        val arr = str.split(" ")
        arr.map(f => f.substring(0, 1).toUpperCase + f.substring(1, f.length)).mkString(" ")
      }
    }

    //Using with DataFrame
    val convertUDF = udf(convertCase)
    df.select(col("Seqno"),
      convertUDF(col("Quote")).as("Quote") ).show(false)

    // Using it on SQL
    spark.udf.register("convertUDF", convertCase)
    df.createOrReplaceTempView("QUOTE_TABLE")
    spark.sql("select Seqno, convertUDF(Quote) from QUOTE_TABLE").show(false)


*/




  }

}
