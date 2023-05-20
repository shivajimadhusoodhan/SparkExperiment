name := "SparkExportJob"

version := "0.1"

scalaVersion := "2.12.8"
//sparkVersion := "3.2.0"


// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.0"

//libraryDependencies += "org.apache.spark" %% "spark-avro" % "3.2.0"

// https://mvnrepository.com/artifact/com.github.scopt/scopt
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1"

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

// https://mvnrepository.com/artifact/org.apache.spark/spark-hadoop-cloud
libraryDependencies += "org.apache.spark" %% "spark-hadoop-cloud" % "3.2.0"

libraryDependencies += "org.yaml" % "snakeyaml" % "1.29"

// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-aws
//libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.2.0"

// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bundle
//libraryDependencies += "com.amazonaws" % "aws-java-sdk-bundle" % "1.11.563"




// https://mvnrepository.com/artifact/com.azure/azure-storage-blob
//libraryDependencies += "com.azure" % "azure-storage-blob" % "12.8.0"


