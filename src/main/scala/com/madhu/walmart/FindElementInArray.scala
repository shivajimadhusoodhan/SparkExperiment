package com.madhu.walmart

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import java.util

//expected result
/*+---+-------+------+
| id|company|salary|
+---+-------+------+
|  6|      A|   513|
|  5|      A|   451|
| 12|      B|   234|
|  9|      B|  1154|
| 14|      C|  2645|
+---+-------+------+*/

object FindElementInArray extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      (1,"A",2341),
      (2,"A",341),
      (3,"A",15),
      (4,"A",15314),
      (5,"A",451),
      (6,"A",513),
      (7,"B",15),
      (8,"B",13),
      (9,"B",1154),
      (10,"B",1345),
      (11,"B",1221),
      (12,"B",234),
      (13,"C",2345),
      (14,"C",2645),
      (15,"C",2645),
      (16,"C",2652),
      (17,"C",65)).toDF("id", "company", "salary")

    val calculateMidNum = (num: Int) => {
      if(num %2 == 0) List(num/2, num/2 + 1)
      else List(num/2 +1)
    }
    val midNumUdf = udf(calculateMidNum)


    val ascendingWindow = Window.partitionBy("company").orderBy("salary", "id")
    val getMaxWindow = Window.partitionBy("company").orderBy($"ascendingRowOrder".desc)

    val result = df
      .withColumn("ascendingRowOrder", row_number.over(ascendingWindow))
      .withColumn("max_num", first("ascendingRowOrder").over(getMaxWindow))
      .withColumn("mid_num", midNumUdf($"max_num") )
      .filter(array_contains($"mid_num", $"ascendingRowOrder"))
      .drop("ascendingRowOrder", "max_num", "mid_num")
      .orderBy("company")
    result.show()
  }

}
