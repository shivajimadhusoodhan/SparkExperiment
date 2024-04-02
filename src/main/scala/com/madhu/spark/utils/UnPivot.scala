package com.madhu.spark.utils

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions.{expr, regexp_replace, substring}

object UnPivot extends SparkSessionFactory{

  def main(args: Array[String]): Unit = {


    import spark.implicits._

    val inputDF = Seq(
      ("A1", 1, 123, "1/1/2023", "2/2/3023", "3/3/2023", 3,5,0),
      ("A1", 1, 456, "1/2/2023", "3/3/2023", "3/1/2023", 2,7,4)
    ).toDF("code", "number", "order", "start_date_PRE", "start_date_MIG", "start_date_POST", "PRE", "MIG", "POST")

    inputDF.show()

    val unpivotDF1 = inputDF.select($"code", $"number",$"order",
      expr("stack(3, 'PRE', PRE, 'MIG', MIG, 'POST', POST) as (status, time)"))
      .where("time > 0")

    unpivotDF1.show()


    val unpivotDF2 = inputDF.select($"code", $"number", $"order",
      expr("stack(3, 'start_date_PRE', start_date_PRE, 'start_date_MIG', start_date_MIG, 'start_date_POST', start_date_POST) as (status, start_date)"))
      .withColumn("status", regexp_replace($"status", "start_date_", ""))

    unpivotDF2.join(unpivotDF1, Seq("code", "number", "order", "status")).show()
  }


}
