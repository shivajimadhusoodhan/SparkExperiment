package com.madhu.nutriasia.salestotrade

import com.madhu.nutriasia.ConfigReader
import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{col, expr, lit, when}
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

    val RKF_DF = addRkfBasedOnRkfList(rkfConfig.rkfEntities, CP_DF)

    val CKF_DF = addCkfBasedOnCkfList(rkfConfig.ckfEntities, RKF_DF)
    CKF_DF.show(false)
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

  def addCkfBasedOnCkfList(ckfEntities: List[CalcKeyFigureEntity], inputDF: DataFrame) = {
    ckfEntities.foldLeft(inputDF)((tempDF, ckfEntity) => {
      tempDF.withColumn(ckfEntity.calcKeyFigureName, expr(ckfEntity.calcKeyFigureExpression))
    })
//    inputDF.withColumn("stt_quantity", expr("STT_Quantity_Original + STT_Other_Channels_QTY"))
  }
}
