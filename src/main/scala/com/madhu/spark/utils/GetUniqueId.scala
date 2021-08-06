package com.madhu.spark.utils

import java.util.UUID
import java.time.Instant

object GetUniqueId {

  def main(args: Array[String]): Unit = {
    println(UUID.randomUUID().toString)
    println(Instant.now.getEpochSecond)
  }

}
