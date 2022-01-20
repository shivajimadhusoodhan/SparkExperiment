package com.madhu.spark.headcount

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object EffectiveDateUpdate  extends  SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    // add ClgInfo column values here
    val clgInfoDF = Seq(
      //("XYZ", "2021-01-01", "2021-04-02"),
      //("XYZ", "2021-01-24", "2021-02-23"),
      ("XYZ", "2021-04-03", "")
      ).toDF("clgUuid", "effectiveStartDate", "effectiveEndDate")
      .select($"clgUuid",  $"effectiveStartDate".cast("date"),  $"effectiveEndDate".cast("date"))
      .withColumn("isLeaver", lit("N"))
      .withColumn("leavingDate", lit(("")).cast("date"))
      //.filter($"df".isin)
    // add Supplement column values here
    val supplDF = Seq(
      ("XYZ", "2021-04-03", "")
    ).toDF("clgUuid", "leavingDate", "effectiveEndDate")
      //.filter($"leavingDate".isNotNull)
      .select($"clgUuid",  $"leavingDate".cast("date"),  $"effectiveEndDate".cast("date"))
      .withColumn("isLeaver", lit("Y"))
      .withColumn("effectiveStartDate", $"leavingDate")

    val intermDF = clgInfoDF.unionByName(supplDF)
   //intermDF.filter($"dfdf".i)

    val effEndDateWindow = Window.partitionBy($"clgUuid").orderBy("effectiveStartDate")
    val leadCol = lead(col("effectiveStartDate"), 1).over(effEndDateWindow)

    val effStartDateWindow = Window.partitionBy($"clgUuid").orderBy("effectiveStartDate")
    val firstEffStartDateCol = first(col("effectiveStartDate")).over(effStartDateWindow)

    val leavingDateWindow = Window.partitionBy($"clgUuid").orderBy($"isLeaver".desc)
    val firstLeaveDateCol = first(col("leavingDate")).over(leavingDateWindow)

    val clgInfoUpdateDF = intermDF
    .withColumn("effectiveEndDate", leadCol)
    .withColumn("leavingDate",firstLeaveDateCol)
    .withColumn("hireDate",firstEffStartDateCol)
    .withColumn("leaveDiff", datediff($"leavingDate", $"effectiveEndDate"))
    .withColumn("leavingDate",when($"leaveDiff" >= 0, lit("")) otherwise $"leavingDate")
    .withColumn("check", $"leaveDiff" < 0 )
    .withColumn("effectiveStartDate", when(($"leaveDiff" < 0), date_add($"effectiveStartDate",1)) otherwise($"effectiveStartDate"))
    .withColumn("effectiveStartDate", when(($"leavingDate" === $"effectiveStartDate"), date_add($"effectiveStartDate",1)) otherwise($"effectiveStartDate"))
    .withColumn("effectiveEndDate", when(($"leaveDiff" > 0), date_sub($"effectiveEndDate",1)) otherwise($"effectiveEndDate"))
    .withColumn("effectiveEndDate", when(($"leaveDiff" < 0), date_sub($"effectiveEndDate",1)) otherwise($"effectiveEndDate"))
      //.drop("leaveDiff", "check", "isLeaver")
      //.select("clgUuid", "effectiveStartDate", "effectiveEndDate", "hireDate", "leavingDate")
    clgInfoUpdateDF.orderBy("clgUuid","effectiveStartDate").show()
  }

}
