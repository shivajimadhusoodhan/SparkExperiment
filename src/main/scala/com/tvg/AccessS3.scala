package com.tvg

import com.madhu.spark.common.SparkSessionFactory

object AccessS3  extends SparkSessionFactory {

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    /*
    SETTING THE PROFILE
    1. keep profile name same in config and credentials file
    2. If you are using AWS_PROFILE  env variable then make sure same profile exists and set fs.s3a.aws.credentials.provider
    3. If you are NOT using AWS_PROFILE  env variable then make sure
       edit configuration and select currently selected AWS connection and no need of fs.s3a.aws.credentials.provider
     */

   // spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.eu-west-1.amazonaws.com")
    //spark.sparkContext.hadoopConfiguration.set("fs.s3a.aws.credentials.provider", "com.amazonaws.auth.profile.ProfileCredentialsProvider")
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", "")
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", "aws secretkey value")

    val df = spark.read.csv("s3a://dev-hubs-temp/source/CAMPAIGN_EVENT_20120421.csv")
    df.show(3)

  }
}
