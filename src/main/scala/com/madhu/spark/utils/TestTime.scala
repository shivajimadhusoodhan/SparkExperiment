package com.madhu.spark.utils

import java.time.{Clock, LocalDateTime, LocalTime, ZoneOffset}

object TestTime {

  private def epochToLocalDateTime: Long => LocalDateTime = (epochTime: Long) =>
    LocalDateTime.ofEpochSecond(epochTime, 0, ZoneOffset.UTC)
      .plusSeconds(36)

  private def getCurrentTimeInUTC: LocalDateTime = LocalDateTime.now(Clock.systemUTC())

  def main(args: Array[String]): Unit = {

    //val ti: Long = 1626280014254

    println(epochToLocalDateTime(1626280014241L/1000)) //2021-07-14T16:26:54
    println(epochToLocalDateTime(1626280014))
    println(getCurrentTimeInUTC)

  }

}
