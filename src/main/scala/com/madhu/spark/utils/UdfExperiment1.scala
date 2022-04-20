package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory

object UdfExperiment1  extends  SparkSessionFactory{

  case class Director(firstName: String, lastName: String, movieName: String)

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val deltaDF = Seq(
      Director("Kish", "jen", "G-A"),
      Director("Kish", "Kirhs", "G-B"),
      Director("man", "Mandir", "G-C"),
      Director("Ram", "lakhn", "G-C")
    ).toDS()

    import spark.implicits._

    deltaDF.filter(sameLetter _).show()

  }

  def sameLetter(director: Director): Boolean = {
    val firstNameLetter = director.firstName.charAt(0).toLower
    val secondNameLetter = director.lastName.charAt(0).toLower

    firstNameLetter == secondNameLetter//)??(true , false)

  }

}
