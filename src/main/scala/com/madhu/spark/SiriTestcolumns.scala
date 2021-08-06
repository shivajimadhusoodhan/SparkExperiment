package com.madhu.spark

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._

import scala.reflect.runtime.currentMirror
import scala.util.{Failure, Success, Try}

object SiriTestcolumns extends SparkSessionFactory {




  def main(args: Array[String]): Unit = {

    import spark.implicits._

    val addSlot = udf{
      (col: String) => "slot_".concat(col)
    }
    val df2 = Seq(
      ("1", "4", 3),
      ("1", "3", 4),
      ("1", "5", 5),
      ("1", "1", 6),
      ("2", "2", 7),
      ("3", "6", 4)
    ).toDF("id", "name", "marks")
      //.join(df1.alias("d"), Seq("id"))

    val selectedCols: Seq[String] => Seq[String] = (slotList: Seq[String]) =>
      Seq( "id", "name") ++ slotList ++  Seq("driverValue")

    val df3= df2
      .withColumn("name", addSlot($"name"))
      .groupBy("id")
      .pivot("name")
      .sum("marks")
     //.show()
val slotColList = df3.columns
  .filter(_.startsWith("slot")).sorted.toSeq
 // .foreach(println)

    df3.select(selectedCols(slotColList).map(col):_*)

    val selectCols = Seq("id", "d.name", "dept", "dept11").map(col)
    val renameCols = Seq("new_id", "new_name", "new_dept", "new_dept11")
   // df2.select(selectCols:_*).toDF(renameCols:_*).show(2)

  }

}
