package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object UnionByNameExp extends SparkSessionFactory{


  def unionByName(df1: DataFrame, df2: DataFrame) = {
    if (df1.columns.size != df2.columns.size)
      throw new Exception("Dataframe has no equal columns")

    val df1Columns = df1.columns
    val df2Columns = df2.columns
    var newDfOrder = new Array[String](df1.columns.size)

    for(i <- 0 to df1.columns.size - 1) {
      if(df2Columns.contains(df1Columns(i))) {
        newDfOrder(i) = df1Columns(i)
      } else {
        throw new Exception("Dataframe has different column names")
      }
    }

    df1.union(df2.select(newDfOrder.toSeq.map(col):_*))
  }
  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("a1", "4", 3),
      ("s2", "3", 4),
      ("d3", "5", 5),
      ("f4", "1", 6),
      ("g5", "2", 7),
      ("h6", "6", 4)).toDF("dept", "Id", "marks2")

    val df2 = Seq(
      ("1", "", 3),
      ("1", "fai", 4),
      ("1", "ty", 5),
      ("2", "FG", 6),
      ("2", "fg", 7),
      ("3", "fkj", 4)).toDF("Id", "dept", "marks2")

    val newDF = unionByName(df1, df2)

    newDF.show()
  }


}
