package com.madhu.spark.utils

object CaseTest {

  case class Person(name: String, dept: Option[String] = Some("UFS"))

  def main(args: Array[String]): Unit = {

    val k = Person("Kamalesh", null)

    println(k.toString)
  }

}
