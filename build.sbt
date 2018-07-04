name := "template"

version := "0.1"

scalaVersion := "2.12.6"
val circeVersion = "0.9.3"

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.12" % "3.0.5" % "test",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.lihaoyi" %% "scalatags" % "0.6.7",
  "com.github.japgolly.scalacss" %% "ext-scalatags" % "0.5.3",
  "com.github.japgolly.scalacss" %% "core" % "0.5.3")

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

