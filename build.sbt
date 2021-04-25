name := "tips"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions ++=
  "-deprecation" ::
    Nil

/**
 * Runtime deps
 */
libraryDependencies ++=
  "com.typesafe.akka" %% "akka-http" % "10.0.10" ::
    "de.heikoseeberger" %% "akka-http-play-json" % "1.18.0" ::
    Nil

/**
 * Test deps
 */
libraryDependencies ++=
  "org.scalatest" %% "scalatest" % "3.0.1" % Test ::
    "org.scalamock" %% "scalamock" % "4.0.0" % Test ::
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10" % Test ::
    Nil


mainClass := Some("com.livesafe.tips.TipsBoot")