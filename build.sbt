name        := "Scala-Java-exercises"
version     := "0.1"
description := "Project contains the sample code written in Scala and Java"

scalaVersion in ThisBuild := "2.13.2"

// PROJECTS
lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(`Scala-module`)

lazy val `Scala-module` = project
  .settings(
    name := "Scala-module",
    settings,
    libraryDependencies ++= scalaDependencies ++ logging
  )

lazy val dependencies =
  new {
    val parallelColl= "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"
    val akka = "com.typesafe.akka" %% "akka-actor" % "2.6.5"
    val scalafx = "org.scalafx" %% "scalafx" % "14-R19"
    val scalaTest = "org.scalatest" %% "scalatest" % "3.2.0" % "test"
  }

lazy val scalaDependencies = Seq(
  dependencies.akka,
  dependencies.parallelColl,
  dependencies.scalafx,
  dependencies.scalaTest
)

lazy val logging = Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)

lazy val settings = Seq(
  scalacOptions ++= compilerOptions
)

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)