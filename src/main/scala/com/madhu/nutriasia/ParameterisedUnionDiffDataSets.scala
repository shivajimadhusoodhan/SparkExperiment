package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, when}

object ParameterisedUnionDiffDataSets extends SparkSessionFactory with ConfigReader {


  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "HumanicaMY", "IAM", 3),
      ("a2", "HumanicaTH", "IAM", 0),
      ("a3", "XPD8", "IAM", 0),
      ("a4", "Tesco Mobile", "IAM", 7))
      .toDF("cid", "ciam_source", "source_system", "maths")
    df1.show()

    val appProps = getAppProperties("AppProps.json")

    val df2 = Seq(
      ("a1", "HumanicaMY", "IAM", 23),
      ("a2", "HumanicaTH", "IAM", 23),
      ("a3", "XPD8", "IAM", 20),
      ("a4", "Tesco Mobile", "IAM", 37)).toDF("sid", "iam_source", "ssource_system", "science")
    df2.show()

    val df3 = Seq(
      ("a1", "HumanicaMY", "IAM"),
      ("a2", "HumanicaTH", "IAM"),
      ("a3", "XPD8", "IAM"),
      ("a4", "Tesco Mobile", "IAM")).toDF("id", "source", "source_system")
      .withColumnRenamed("source", "iam_source")
    df3.show()

    val df1Mapping = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals ("table1")).get("table1").get

    val df1Processed = renameColumnsBasedOnMapping(df1, df1Mapping)

    val df2Mapping: Seq[SourceTargetMapping] = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals ("table2")).get("table2").get

    //val df2Processed = renameColumnsBasedOnMapping(df2, df2Mapping)

//    val finalDF = df1Processed
//      .unionByName(df2Processed, true)
//      .unionByName(df3, true)
//
//    finalDF.show()

  }

  def renameColumnsBasedOnMapping(devDF: DataFrame, mappingColumns: List[SourceTargetMapping]): DataFrame = {
    import spark.implicits._
    mappingColumns.foldLeft(devDF)((tempDF, mapping) => {
      tempDF.withColumnRenamed(mapping.sourceField, mapping.targetField)
    })
  }

  def transformDataframe(df: DataFrame, mappings: List[TransformationMapping]): DataFrame = {
    mappings.foldLeft(df)((tempDF, mappings) => {
      val condition = when(col(mappings.sourceColumnName) isin (mappings.valuesList:_*), col(mappings.sourceColumnName))
      tempDF.withColumn(mappings.newColumnName, condition)
    })
  }
}
