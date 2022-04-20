package com.madhu.spark.utils

import java.time.{Clock, LocalDate, LocalDateTime, LocalTime, ZoneOffset}

object TestTime {

  private def epochToLocalDateTime: Long => LocalDateTime = (epochTime: Long) =>
    LocalDateTime.ofEpochSecond(epochTime, 0, ZoneOffset.UTC)
      .plusSeconds(36)

  private def getCurrentTimeInUTC: LocalDateTime = LocalDateTime.now(Clock.systemUTC())

  def main(args: Array[String]): Unit = {

    //val ti: Long = 1626280014254

    /*println(epochToLocalDateTime(1626280014241L/1000)) //2021-07-14T16:26:54
    println(epochToLocalDateTime(1626280014))
    println(getCurrentTimeInUTC)*/

    //println(LocalDate.parse("2021-10-23"))
    val date1 = LocalDate.parse("2021-11-28")

    val p9_start = LocalDate.parse("2021-11-01")
    val p9_end = LocalDate.parse("2021-11-28")

    val p10_start = LocalDate.parse("2021-11-01")
    val p10_end = LocalDate.parse("2021-11-28")

    val p11_start = LocalDate.parse("2021-11-01")
    val p11_end = LocalDate.parse("2021-11-28")

    println((date1.isAfter(p9_start) && date1.isBefore(p9_end)) || date1.isEqual(p9_start) || date1.isEqual(p9_end))
println("2021-06-09T11:21:49.677Z".substring(0,10))

    println(p9_end.toString)
  }

}
