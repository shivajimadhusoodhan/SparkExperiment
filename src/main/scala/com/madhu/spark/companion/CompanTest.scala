package com.madhu.spark.companion

object CompanTest {

  def main(args: Array[String]): Unit = {
    val connectors = Connectors()
    println(connectors.hashCode())
    println(connectors.getSub(323))

    val connectors1 = Connectors()
    println(connectors1.hashCode())
    println(connectors1.getSum(1,2))
  }
}
