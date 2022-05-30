package com.madhu.walmart

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DecimalType

object PercentileProb extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      ("A", 2341),
      ("A", 341),
      ("A", 15),
      ("A", 15314),
      ("A", 451),
      ("A", 513),
      ("B", 15),
      ("B", 13),
      ("B", 1154),
      ( "B", 1345),
      ( "B", 1221),
      ( "B", 234),
      ( "C", 2345),
      ( "C", 2645),
      ( "C", 2645),
      ( "C", 2652),
      ( "C", 65)).toDF( "company", "salary")

    val lagWindow = Window.partitionBy("company").orderBy("company")

    val getPercentageIncrease = (curr: BigDecimal, prev: BigDecimal) => {
      if(prev == 0.0)
        curr
      else
        curr/prev
        //((curr - prev)/prev)*100
    }

    val percentageIncreaseUdf = udf(getPercentageIncrease)

    val result = df
      //.sort($"salary")
      .withColumn("salary", $"salary".cast(DecimalType(25, 2)))
      .withColumn("lag_col", lag("salary", 1).over(lagWindow))
      .na.fill(0, Seq("lag_col"))
      .withColumn("revenue_increase", (($"salary" - $"lag_col")*100)/$"lag_col")
      .withColumn("revenue_increase1", bround($"revenue_increase"))
//      .withColumn("revenue_increase", percentageIncreaseUdf($"salary", $"lag_col"))

    result.show()

  }

}
