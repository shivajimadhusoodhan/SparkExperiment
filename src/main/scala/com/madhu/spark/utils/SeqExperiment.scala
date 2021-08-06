package com.madhu.spark.utils

object SeqExperiment {

  def main(args: Array[String]): Unit = {
    val wce_15min_dept_level_columns = Seq (
      "ID",
      "VERSION",
      "RETAIL_NUMBER",
      "DEPARTMENT_NAME",
      "DAY"
    ) ++
      (1 to 96).map(num => "shaped_workload_people_".concat(num.toString)) ++
      Seq(
        "APPID",
        "WEEK",
        "DEPARTMENT_CODE"
      )
     // .toSeq
    wce_15min_dept_level_columns.foreach(println)
  }

}
