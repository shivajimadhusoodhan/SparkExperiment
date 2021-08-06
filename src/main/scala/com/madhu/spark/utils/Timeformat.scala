package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

import java.sql.Date
import java.time.{DayOfWeek, LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import scala.collection.mutable.Map
import org.apache.spark.sql.functions._

object Timeformat extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df1 = Seq(
      (1, "4", 3),
      (2, "3", 4),
      (3, "5", 5),
      (4, "1", 6),
      (5, "2", 7),
      (7, "89", 7),
      (6, "6", 4)).toDF("Id", "Actual_Name", "marks")

    val getDateUdf = udf {
      (dayOfWeek: Int) =>
        val currentWeekDateMap = getDatesMapForCurrentWeek()
        currentWeekDateMap(dayOfWeek).toString
    }


    val df2 = df1
      .withColumn("calendar_date", concat(lit("scheduled on "), getDateUdf($"Id")))

    df2.show(false)

    df2.printSchema()


  }

  def getDatesMapForCurrentWeek() = {
    var currentDate = LocalDate.now()
    var currentWeekMap = Map[Int, LocalDate]()

    for(day <- 1 to 7) {
       currentDate.getDayOfWeek match {
         case DayOfWeek.MONDAY => currentWeekMap += (1 -> currentDate)
         case DayOfWeek.TUESDAY => currentWeekMap += (2 -> currentDate)
         case DayOfWeek.WEDNESDAY => currentWeekMap += (3 -> currentDate)
         case DayOfWeek.THURSDAY => currentWeekMap += (4 -> currentDate)
         case DayOfWeek.FRIDAY => currentWeekMap += (5 -> currentDate)
         case DayOfWeek.SATURDAY => currentWeekMap += (6 -> currentDate)
         case DayOfWeek.SUNDAY => currentWeekMap += (7 -> currentDate)
       }
      currentDate = currentDate.plusDays(1)
    }

    currentWeekMap

  }


}
