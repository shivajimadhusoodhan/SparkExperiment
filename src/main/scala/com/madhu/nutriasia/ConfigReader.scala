package com.madhu.nutriasia

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



 /* def main(args: Array[String]): Unit = {
    //val appProps = getAppProperties("/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/AppProps.json")
    val appProps = getAppProperties("AppProps.json")

    //val mappp: Seq[RowFieldMapping] = appProps.rowFieldMapping.filter(_.tableName.equals("df1")).

    val df1Mapping = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals("df1")).get("df1")
    println(df1Mapping.get)

  }*/



}
