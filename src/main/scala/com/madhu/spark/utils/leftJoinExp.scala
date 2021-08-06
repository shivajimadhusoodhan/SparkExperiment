package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object LeftJoinExp  extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      ("1", "4", 3),
      ("2", "3", 4),
      ("3", "5", 5),
      ("4", "1", 6),
      ("5", "2", 7),
      ("6", "6", 4)).toDF("Id", "Actual_Name", "marks")

    val df2 = Seq(
      ("1", "", 3),
      ("1", "fai", 4),
      ("1", "ty", 5),
      ("2", "FG", 6),
      ("2", "fg", 7),
      ("3", "fkj", 4)).toDF("Id", "dept", "marks2")



    /*df2.filter('dept.startsWith("read")).count() match {
      case 0L => proceedToCheckEmptyStatus(df2)
      case _ => println("one job is yet to be processed status")
    }

    def proceedToCheckEmptyStatus(df: DataFrame) = {
      df2.filter('dept === "").count() match {
        case 0L =>  println("no empty status")
        case _ => println("execute the job")
      }*/
    val idSeq = Seq("1", "2")
    df1.filter($"id".isin(idSeq:_*)).show()


  }

}
