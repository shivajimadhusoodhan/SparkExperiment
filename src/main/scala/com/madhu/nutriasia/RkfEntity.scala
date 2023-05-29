package com.madhu.nutriasia

case class RkfEntity(
                      rkfName: String,
                      fetchFromColumn: String,
                      rkfConditions: List[RkfCondition]
                    )

case class RkfCondition(
                        filterColumnName: String,
                        filterOperator: String,
                        filterValue: List[String],
                        startingValue: Option[String],
                        endingValue: Option[String]
                       )
