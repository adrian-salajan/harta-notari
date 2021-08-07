Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.6"

// enablePlugins(JavaAppPackaging)
// ThisBuild / maintainer := "blitzkrieg.adrian@gmail.com"

lazy val root = (project in file("."))
.aggregate(s3Java, geocode)
.settings(
    name := "harta-anotari"
  )
  

  lazy val awsOrg = "com.amazonaws"
  lazy val vLog4J = "2.13.0"

lazy val s3Java = 
  (project in file("s3-java"))
    .enablePlugins(JavaAppPackaging)
    .settings(
      autoScalaLibrary := false,
      maintainer := "blitzkrieg.adrian@gmail.com",
      libraryDependencies ++= Seq(
          awsOrg % "aws-xray-recorder-sdk-bom" % "2.4.0",
          awsOrg % "aws-lambda-java-core" % "1.2.1",
          awsOrg % "aws-lambda-java-events" % "2.2.9",
          awsOrg % "aws-java-sdk-s3" % "1.11.578",
          awsOrg % "aws-xray-recorder-sdk-core" % "2.9.1",
          awsOrg % "aws-xray-recorder-sdk-core" % "2.9.1",
          awsOrg % "aws-xray-recorder-sdk-aws-sdk-instrumentor" % "2.9.1",
          "com.google.code.gson" % "gson" % "2.8.6",
          "org.apache.logging.log4j" % "log4j-api" % vLog4J,
          "org.apache.logging.log4j" % "log4j-core" % vLog4J,
          "org.apache.logging.log4j" % "log4j-slf4j18-impl" % vLog4J,
          "com.opencsv" % "opencsv" % "5.5",
          awsOrg % "aws-lambda-java-log4j2" % "1.2.0",
          awsOrg % "aws-lambda-java-log4j2" % "1.2.0",
          "org.junit.jupiter" % "junit-jupiter-api" % "5.6.0" % Test,
          "org.junit.jupiter" % "junit-jupiter-engine" % "5.6.0" % Test,
      )
    )

lazy val geocode =  
  (project in file("geocode"))
  .settings(
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "1.0.10",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
      "io.github.vigoo" %% "zio-aws-core" % "3.17.8.4",
      "dev.zio" %% "zio-config" % "1.0.6"
    )
  )

