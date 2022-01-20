package com.madhu.spark.utils

case class OptExtSystem (externalSystems: Option[ExternalSystems])
case class ExternalSystems(
                            sourceSystem: Option[String],
                            iam: Option[Iam],
                            hcm: Option[Hcm]
                          )
case class Hcm(
                id: Option[String]
              )

case class Iam(
                source: Option[String]
              )

object OptionsExp {


  def main(args: Array[String]): Unit = {

    val externalSystem = ExternalSystems(
      Some("Oracle"),
      Some(Iam(Some("iam"))),
     None
    )

    val optExtSystem = Some(externalSystem)

    val hcmId = optExtSystem.flatMap(ext => ext.hcm.flatMap(_.id)).getOrElse("")


    println("adjfh"+ hcmId)

  }

}
