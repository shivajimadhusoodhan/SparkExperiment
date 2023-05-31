package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.{col, lit, sum, when}

object RKFonDataSets extends SparkSessionFactory with ConfigReader {

  val filterIds = List("a1", "a5")

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "HumanicaMY", "IAM", 3),
      ("a2", "Tesco Mobile", "IAM", 5),
      ("a5", "Tesco Mobile", "SH", 1),
      ("a3", "XPD8", "SM", 2),
      ("a4", "HumanicaTH", "SM", 7))
      .toDF("id", "iam_source", "source_system", "maths")

    df1.show(false)
//    df1
//      .withColumn("science", lit(1))
//      .withColumn("total", col("science") + $"maths")
//      .show()

    /*df1
      .withColumn("newExp", when(col("iam_source").rlike("Huma*") || col("iam_source").rlike("Tes*"), lit("crazy")))
      .show()
*/
    val rkfs = getRkfProperties("RkfConditions.json")

    val rkfCondition = getRkfCondition(rkfs.rkfConditions, rkfs.fetchFromColumn)

    val scienceRKF = df1
      .withColumn(rkfs.rkfName, rkfCondition)

    println("Printing Result")
    scienceRKF.show()
  }

  def getRkfCondition(rkfConditions: List[RkfCondition], fetchFrom: String) = {

    val allConditions: Seq[Column] = rkfConditions.map(cond => getConditionBasedOnFilterValue(cond))
    when(allConditions.toList.reduce((x, y) => x && y), col(fetchFrom)) otherwise(lit(0))
  }

  def getConditionBasedOnFilterValue(rkfCondition: RkfCondition) = {
    rkfCondition.filterOperator match {
      case "IS_IN" => col(rkfCondition.filterColumnName) isin (rkfCondition.filterValue:_*)
      case "NOT_IN" => !(col(rkfCondition.filterColumnName) isin (rkfCondition.filterValue:_*))
      case "LIKE" => getContainsPatternCondition(rkfCondition.filterValue, rkfCondition.filterColumnName)
      case "DYNAMIC" => col(rkfCondition.filterColumnName) isin (filterIds: _*)
    }
  }

  def getContainsPatternCondition(rkfFilterValues: List[String], rkfFilterColumnName: String) = {
    rkfFilterValues
      .map(filterValue => col(rkfFilterColumnName) rlike (filterValue))
      .reduce((x, y) => x || y)
  }

}
