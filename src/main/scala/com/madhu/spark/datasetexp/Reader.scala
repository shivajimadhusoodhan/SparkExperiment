package com.madhu.spark.datasetexp

import scala.reflect.runtime.universe.TypeTag // used to provide type information of the case class at runtime
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoder, SparkSession}


case class SubDepartmentEntity(
                                subDepartmentCode: String,
                                subDepartmentShortName: String,
                                subDepartmentFullName: String,
                                subDepartmentShortDescription: String,
                                parentDepartmentCode: String
                              )

object Reader  {

  // T : TypeTag means that an implicit value of type TypeTag[T] must be available at the method call site. Scala will automatically generate this for you. See [here][3] for further details.
  def schemaOf[T: TypeTag]: StructType = {
    ScalaReflection
      .schemaFor[T] // this method requires a TypeTag for T
      .dataType
      .asInstanceOf[StructType] // cast it to a StructType, what spark requires as its Schema
  }

  def readCsvFile[T : Encoder : TypeTag](location: String,
                  fieldSeparator: String = ",",
                  hasHeaders: Boolean = true,
                  escapeChar: String = "\""
                 )(implicit spark: SparkSession) : Dataset[T] = {

    spark
      .read
      .option("header", hasHeaders)
      .option("sep", fieldSeparator)
      .option("inferSchema", false)
      .option("encoding", "UTF-8")
      .option("escape", escapeChar)
      .schema(schemaOf[T])
      .csv(location)
      .as[T]
  }



  def main(args: Array[String]): Unit = {

    implicit val spark = SparkSession.builder().master("local").getOrCreate()

    import spark.implicits._

    val subDeptDF = readCsvFile[SubDepartmentEntity]("C:\\Users\\printJul.csv")

    subDeptDF.printSchema()
  }


}
