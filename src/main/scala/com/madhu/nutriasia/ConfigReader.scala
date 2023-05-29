package com.madhu.nutriasia

import com.madhu.nutriasia.salestotrade.GlobalRkfConfig
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

trait ConfigReader {

  implicit val formats: DefaultFormats.type = DefaultFormats

  def getAppProperties(confPath: String): ApplicationPropertiesEntity = {

    val jsonString = confPath.startsWith("/") match {
      case true => Source.fromFile(confPath).mkString
      case false => Source.fromResource(confPath).mkString
    }
    parse(jsonString).extract[ApplicationPropertiesEntity]
  }

  def getRkfProperties(confPath: String): RkfEntity = {

    val jsonString = confPath.startsWith("/") match {
      case true => Source.fromFile(confPath).mkString
      case false => Source.fromResource(confPath).mkString
    }
    parse(jsonString).extract[RkfEntity]
  }


  def getSalesToTradeQuery01Properties(confPath: String): GlobalRkfConfig = {

    val jsonString = confPath.startsWith("/") match {
      case true => Source.fromFile(confPath).mkString
      case false => Source.fromResource(confPath).mkString
    }
    parse(jsonString).extract[GlobalRkfConfig]
  }

 /* def main(args: Array[String]): Unit = {
    //val appProps = getAppProperties("/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/AppProps.json")
    val appProps = getAppProperties("AppProps.json")

    //val mappp: Seq[RowFieldMapping] = appProps.rowFieldMapping.filter(_.tableName.equals("df1")).

    val df1Mapping = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals("df1")).get("df1")
    println(df1Mapping.get)

  }*/



}
