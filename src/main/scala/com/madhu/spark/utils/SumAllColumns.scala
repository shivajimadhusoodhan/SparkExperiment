package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions._

object SumAllColumns  extends  SparkSessionFactory{

  def getAllSlotColumns = {
    val abc: Seq[Column] = (1 to 2).map(_.toString).map(num => num.length match {
      case 1 => "marks".concat(num)
      case 2 => "SLOT".concat(num)
    }).map(col)
    array(abc:_*)
  }

  val sumOfColumns = (slots: Seq[Double]) => slots.foldLeft(0.0)(_ + _)
  val sumOfCol = (slot1: Seq[Double]) => slot1.foldLeft(0.0)(_ +_)

  val sumOfSlots = udf(sumOfCol)

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val slotCols = getAllSlotColumns

    val df1 = Seq(
      ("1", "4", 3, 3),
      ("2", "3", 4, 3),
      ("3", "5", 5, 3),
      ("4", "1", 6, 3),
      ("5", "2", 7, 3),
      ("6", "6", 4, 3)).toDF("Id", "Actual_Name", "marks1", "marks2")
      .withColumn("sum", sumOfSlots(slotCols))

     // .withColumn("sum", sumOfSlots(Seq(col("marks1"), col("marks2"))))

    df1.show(20)

    //getAllSlotColumns.foreach(println)

  }

  def mai12n(args: Array[String]): Unit = {
    import org.apache.spark.sql.functions.{udf, array, lit}
    import spark.implicits._

    val myConcatFunc = (xs: Seq[Any], sep: String) =>
      xs.filter(_ != null).mkString(sep)

    val myConcat = udf(myConcatFunc)

    val  df = Seq(
      (null, "a", "b", "c"), ("d", null, null, "e")
    ).toDF("x1", "x2", "x3", "x4")

    val cols = array($"x1", $"x2", $"x3", $"x4")
    val sep = lit("-")

    df.select(myConcat(cols, sep).alias("concatenated")).show

  }

}
