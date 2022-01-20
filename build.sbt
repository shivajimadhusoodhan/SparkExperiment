name := "SparkExportJob"

version := "0.1"

scalaVersion := "2.12.8"


// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.2"

libraryDependencies += "org.apache.spark" %% "spark-avro" % "3.0.2"

// https://mvnrepository.com/artifact/com.github.scopt/scopt
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1"

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test




// https://mvnrepository.com/artifact/com.azure/azure-storage-blob
//libraryDependencies += "com.azure" % "azure-storage-blob" % "12.8.0"


