package com.madhu.spark

import com.madhu.spark.common.SparkSessionFactory
import com.madhu.spark.utils.ExportUtils
import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import scopt.OptionParser

object ExportJob extends SparkSessionFactory {

  val log: Logger = LogManager.getLogger(this.getClass)

  case class ExportConfig(
                         inputFilePath: String = "inputFilePath",
                         outputFilePath: String = "outputFilePath"
                         )

  def configParser(): OptionParser[ExportConfig] =
    new scopt.OptionParser[ExportConfig]("get data from hive table") {
      opt[String]("inputFilePath") required() action { (x, c) ⇒
        c.copy(inputFilePath = x)
      } text ("Path of Incoming file")
      opt[String]("outputFilePath") required() action { (x, c) ⇒
        c.copy(outputFilePath = x)
      } text ("Output file path")

      version("1.0")
      help("help") text "help text"
    }

  def invokeExportHiveJob(config: ExportConfig): Unit = {

    val filePath = config.inputFilePath

    log.info(s"Reading data from ${filePath}")

    val csvDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", false)
      .csv(filePath)

    val finalDF = csvDF
      .withColumn("start_year_week_number", lit(202015))

    ExportUtils.writeToCsv(config.outputFilePath, "TRAVEL_FACTOR.csv", finalDF)

    log.info(s"Finished writing the data to ${config.outputFilePath}")
  }

//  def main(args: Array[String]): Unit = {
//
//    log.info("Initiating the Hive Export job")
//
//    configParser().parse(args, ExportConfig()) match {
//      case Some(config) => invokeExportHiveJob(config)
//      case None =>
//        log.error("Arguments could not be parsed")
//        System.exit(1)
//    }
//  }

  def main(args: Array[String]): Unit = {
    val inputFilePath = "C:\\Users\\factor.csv"
    val outputFilePath = "C:\\Users\\files\\output"
    log.info(s"Reading data from ${inputFilePath}")

    val csvDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", false)
      .csv(inputFilePath)

    val finalDF = csvDF
      .withColumn("week_number", lit(202015))

    ExportUtils.writeToCsv(outputFilePath, "FACTOR.csv", finalDF)
  }

}
