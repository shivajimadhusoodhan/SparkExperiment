package com.madhu.nutriasia.salestotrade

case class GlobalRkfConfig(
                            rkfEntities: List[RestKeyFigureEntity],
                            ckfEntities: List[CalcKeyFigureEntity],
                            logicalNameMapping: Map[String, String]
                          )

case class RestKeyFigureEntity(
                      rkfName: String,
                      fetchFromColumn: String,
                      rkfConditions: List[RestKeyFigureCondition]
                    )

case class RestKeyFigureCondition(
                        filterColumnName: String,
                        filterOperator: String,
                        filterValue: List[String],
                        otherWiseValue: Option[Any],
                        startValue: Option[Any],
                        endValue: Option[Any]
                       )

case class CalcKeyFigureEntity (
                                calcKeyFigureName: String,
                                calcKeyFigureExpression: String
                               )