package com.madhu.spark.implicitExp

trait Animals {

  val isWild = "No"
  val isVeg = "yes"

  implicit val isMammal = true



}
