package com.madhu.nutriasia.salestotrade

import com.madhu.nutriasia.ConfigReader
import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{col, lit, when}
import org.apache.spark.sql.{Column, DataFrame}

object SalesToTradeBexQuery01 extends SparkSessionFactory with ConfigReader {

  val filterCalMonthValues = List("JAN,2021", "FEB,2021")

  def main(args: Array[String]): Unit = {

    val CP_DF = spark
      .read
      .option("header", true)
      .csv("/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/SalesToTradeCP_DummyData.csv")
      .cache()
    CP_DF.show(false)

    val rkfConfig = getSalesToTradeQuery01Properties("SalesToTradeRKF_CKF_01.json")

    val resultDF = addRkfBasedOnRkfList(rkfConfig.rkfEntities, CP_DF)
    resultDF.show(false)
  }

  def addRkfBasedOnRkfList(rkfList: List[RestKeyFigureEntity], compDF: DataFrame) = {
    rkfList.foldLeft(compDF)((tempDF, rkfEntity) => {
      val rkfCondition = getRkfCondition(rkfEntity.rkfConditions, rkfEntity.fetchFromColumn)
      tempDF.withColumn(rkfEntity.rkfName, rkfCondition)
    })
  }

  def getRkfCondition(rkfConditions: List[RestKeyFigureCondition], fetchFrom: String) = {

    val allConditions: Seq[Column] = rkfConditions.map(cond => getConditionBasedOnFilterValue(cond))
    when(allConditions.toList.reduce((x, y) => x && y), col(fetchFrom)) otherwise (lit(0))
  }

  def getConditionBasedOnFilterValue(rkfCondition: RestKeyFigureCondition) = {
    rkfCondition.filterOperator match {
      case "IS_IN" => col(rkfCondition.filterColumnName) isin (rkfCondition.filterValue: _*)
      case "NOT_IN" => !(col(rkfCondition.filterColumnName) isin (rkfCondition.filterValue: _*))
      case "LIKE" => getContainsPatternCondition(rkfCondition.filterValue, rkfCondition.filterColumnName)
      case "DYNAMIC" => col(rkfCondition.filterColumnName) isin (filterCalMonthValues: _*)
    }
  }

  def getContainsPatternCondition(rkfFilterValues: List[String], rkfFilterColumnName: String) = {
    rkfFilterValues
      .map(filterValue => col(rkfFilterColumnName) rlike (filterValue))
      .reduce((x, y) => x || y)
  }

}
