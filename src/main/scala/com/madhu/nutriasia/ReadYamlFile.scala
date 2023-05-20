package com.madhu.nutriasia

import org.yaml.snakeyaml.Yaml

import java.io.{File, FileInputStream}
import java.util

case class Employee(name: String, age: Int, city: String)

object ReadYamlFile {

  def main(args: Array[String]): Unit = {

    val yaml = new Yaml()

    // Replace "path/to/your/file.yaml" with the actual path to your YAML file
    val inputStream: FileInputStream = new FileInputStream(new File("/Users/madhusoodhanhv/Repositories/Playground/SparkExperiment/src/main/resources/Mapping.yaml"))

    val data: util.Map[String, Any] = yaml.load(inputStream).asInstanceOf[java.util.Map[String, Any]]

    println(data.get("address"))

    print(data.get("hobbies"))



  }
}
