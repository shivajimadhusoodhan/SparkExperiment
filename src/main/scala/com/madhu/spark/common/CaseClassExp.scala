package com.madhu.spark.common

import java.time.{Clock, LocalDateTime, LocalDate}

object CaseClassExp {

  val separator = "$,$"

  case class Student(
                    id: String,
                    date: String
                    ) {
    override def toString: String = {
      s"id${separator}name\n${this.id}${separator}${this.getCurrentDateInUTC}"
    }

    def getCurrentDateInUTC: String =
      LocalDate.now(Clock.systemUTC()).toString
  }



  def main(args: Array[String]): Unit = {

    val stud1= Student("343", "SriRam")

    println(stud1)
  }
}
