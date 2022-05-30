package com.madhu.walmart

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType

import java.util


/*EXPECTED OUTPUT
| id|p_id| type|
+---+----+-----+
|  1|null| Root|
|  2|   1|Inner|
|  6|   3| Leaf|
|  3|   1|Inner|
|  4|   2| Leaf|
|  5|   2| Leaf|
+---+----+-----+*/

object FindRootInnerAndLeafOfTree  extends  SparkSessionFactory{

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = Seq(
      ("1", null),
      ("2", "1"),
      ("3", "1"),
      ("4", "2"),
      ("5", "2"),
      ("6", "3")
    ).toDF("id", "p_id")

    val p_id_list: util.List[String] = df.select("p_id").filter($"p_id".isNotNull).distinct()
      .map(row => row(0).toString).collectAsList()

    val result = df
      .withColumn("type", when($"id".isInCollection(p_id_list), lit("Inner")) otherwise lit("Leaf"))
      .withColumn("type", when(($"p_id".isNull), lit("Root")) otherwise($"type"))
//"1", "2", "3"
    result.show()
  }

}
