package com.madhu.spark.utils

import com.madhu.spark.ExportJob.spark
import com.madhu.spark.common.SparkSessionFactory
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.sql.{DataFrame, Row}

object WordCountDF  extends SparkSessionFactory {

  val log: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    val sc = spark.sparkContext
    val csvDF = sc.textFile("C:\\Users\\madvenka2\\Downloads\\mismatchCol.csv")

    val finalDF = csvDF
      .flatMap(line => line.split(","))
      .map(word => (word, 1))
      .reduceByKey(_+_)

    finalDF.coalesce(1).saveAsTextFile("C:\\Users\\madvenka2\\Downloads\\MismatchUnknownP10_v2_count")


  }


  def writeDFtoSingleCSV(inputDF: DataFrame, filePath: String) = {
    import scala.collection.JavaConverters._
    import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
    import org.apache.spark.sql.Row

    val dataDF = inputDF.select(inputDF.columns.map(c => inputDF.col(c).cast("string")): _*)
    val headerDF = spark.createDataFrame(List(Row.fromSeq(dataDF.columns.toSeq)).asJava, dataDF.schema)

    headerDF.union(dataDF).write.option("header", false).mode("overwrite").csv(s"${filePath}_temp")

    val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)

    //FileUtil.copyMerge(fs, new Path(s"${filePath}_temp"), fs, new Path(filePath), true, spark.sparkContext.hadoopConfiguration, null)
  }
}