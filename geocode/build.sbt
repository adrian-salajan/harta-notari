lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.6"
    )),
    name := "scalatest-example"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test

libraryDependencies += "dev.zio" %% "zio" % "1.0.10"

libraryDependencies += "io.github.vigoo" %% "zio-aws-core" % "3.17.8.4"

// https://mvnrepository.com/artifact/dev.zio/zio-config
libraryDependencies += "dev.zio" %% "zio-config" % "1.0.6"
