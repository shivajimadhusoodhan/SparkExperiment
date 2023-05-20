package com.madhu.spark

import com.madhu.spark.common.SparkSessionFactory
import org.apache.spark
import org.apache.spark.sql.functions._

object ClgUUidStoreMapper extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {

    val clgEmpFile = "C:\\Users\\madvenka2\\Downloads\\colleagueAPI_CEP_mapping_uuid_id_source.csv"
    val storePersonFile = "C:\\Users\\madvenka2\\Downloads\\DaniFile\\*.csv"

    import spark.implicits._

    val clgEmpFileDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", true)
      .csv(clgEmpFile).cache()


    val storePersonFileDF = spark
      .read
      .option("header", true)
      .option("delimiter", ",")
      .option("escape", "\"")
      .option("inferschema", true)
      .csv(storePersonFile)
      .withColumnRenamed("Store Number", "store_number")
      .withColumnRenamed("Person Number", "employee_number")
      .cache()


   /* clgEmpFileDF.show(3)
    println(s"clgEmpFileDF :: ${clgEmpFileDF.count()}")

    storePersonFileDF.show(3)
    println(s"storePersonFile :: ${storePersonFileDF.count()}")
    println(s"Distinct EmployeeNumber :: ${storePersonFileDF.select(col("employee_number")).distinct().count()}")

    val finalDF = storePersonFileDF
      .join(clgEmpFileDF, Seq("employee_number"))
      .cache()

    println(s"finalDF :: ${finalDF.count()}")*/
    //finalDF.show(20)

//    finalDF.select("store_number", "colleague_uuid")
//      .coalesce(1)
//      .write
//      .option("header", true)
//      .option("encoding", "UTF-8")
//      .option("quoteAll", "false")
//      .option("delimiter", ",")
//      .csv("C:\\Users\\madvenka2\\Downloads\\colleagueStoreMap.csv")


  }
}


