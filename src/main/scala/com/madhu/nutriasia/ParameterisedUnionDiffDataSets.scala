package com.madhu.nutriasia

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

object ParameterisedUnionDiffDataSets extends SparkSessionFactory with ConfigReader {


  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "HumanicaMY", "IAM", 3),
      ("a2", "HumanicaTH", "IAM", 0),
      ("a3", "XPD8", "IAM", 0),
      ("a4", "Tesco Mobile", "IAM", 7))
      .toDF("cid", "ciam_source", "source_system", "maths")

    val filterList = List("a1", "a3")
    val condition = col("cid") isin (filterList:_*)

    df1.filter(condition).show()






    val df2 = Seq(
      ("a1", "HumanicaMY", "IAM", 23),
      ("a2", "HumanicaTH", "IAM", 23),
      ("a3", "XPD8", "IAM", 20),
      ("a4", "Tesco Mobile", "IAM", 37)).toDF("sid", "iam_source", "ssource_system", "science")

    val df3 = Seq(
      ("a1", "HumanicaMY", "IAM"),
      ("a2", "HumanicaTH", "IAM"),
      ("a3", "XPD8", "IAM"),
      ("a4", "Tesco Mobile", "IAM")).toDF("id", "source", "source_system")
      .withColumnRenamed("source", "iam_source")




    //FINAL COLUMNS
    // ID  IAM_SOURCE SOURCE_SYSTEM MATHS SCIENCE

    val appProps = getAppProperties("AppProps.json")

    val df1Mapping = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals ("df1")).get("df1").get
    println(df1Mapping)

    val df1Processed = renameColumnsBasedOnMapping(df1, df1Mapping)
    //df1Processed.show()

    val df2Mapping = appProps.rowFieldMapping.tableMapping.filterKeys(_ equals ("df2")).get("df2").get
    println(df2Mapping)

    val df2Processed = renameColumnsBasedOnMapping(df2, df2Mapping)
    //df2Processed.show()


    val finalDF = df1Processed
      .unionByName(df2Processed, true)
      .unionByName(df3, true)

   // finalDF.show()

  }

  def renameColumnsBasedOnMapping(devDF: DataFrame, mappingColumns: List[SourceTargetMapping]): DataFrame = {
    import spark.implicits._
    mappingColumns.foldLeft(devDF)((tempDF, mapping) => {
      tempDF.withColumnRenamed(mapping.sourceField, mapping.targetField)
    })
  }
}
