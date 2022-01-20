package com.madhu.spark.companion

class Connectors {

  def getSum(a: Int, b: Int) = a+b

  def getSub(a: Int) = 1000-a

}

object Connectors {

  def apply() ={
    new Connectors()
  }
}