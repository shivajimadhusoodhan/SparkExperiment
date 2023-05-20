package com.madhu.nutriasia

import scala.collection.immutable.Map

case class ApplicationPropertiesEntity(
                                        kafkaConfig: KafkaConfig,
                                        azureSqlConfig: AzureSqlConfig,
                                        restApiConfigs: List[RestApiConfig],
                                        rowFieldMapping: RowFieldMapping,
                                        exceptionConfig: ExceptionConfig,  
                                        azureBlobConfig: AzureBlobConfig,
                                        inputTables: InputTables,
                                        azureQueueConfig: AzureQueueConfig,
                                        keyvaultMountPath: Option[String]
                                      )

case class KafkaConfig(
                        broker: String,
                        securityProtocolConfig: String,
                        saslMechanism: String,
                        sslTrustStoreLocation: String,
                        sslTrustStorePassword: String,
                        saslJaasConfig: String
                      )

case class AzureSqlConfig(
                           url: String,
                           username: String,
                           password: String
                         )

case class RestApiConfig(
                          appName: String,
                          username: String,
                          password: String,
                          access_token_url: Option[String],
                          get_url: String
                        )

case class RowFieldMapping(
                            tableMapping: Map[String, List[SourceTargetMapping]]
                         )

case class SourceTargetMapping(
                                sourceField: String,
                                targetField: String
                              )
case class ExceptionConfig(
                            dlqTopic: String,
                            retryDelayTime: Long
                          )

case class AzureBlobConfig (
                             connectionString: String,
                             containerName: String
                           )

case class InputTables (
                         employeeEmployeeMapping: String
                       )

case class AzureQueueConfig (
                              connectionString: String,
                              maxDequeCount: Int,
                              timeToLive: Int,
                              initialVisibilityDelaySeconds: Int,
                              visibilityTimeOutSeconds: Int
                            )

case class SupplApplicationPropertiesEntity(
                                             topologySupplConfigs: List[TopologySuppl]
                                           )

case class TopologySuppl(
                          appName: String,
                          action: String
                        )


