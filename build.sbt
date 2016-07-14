name := "stub-dsl"

version := "1.0"

organization := "dsl"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.4.6",
  "org.scalaj" %% "scalaj-http" % "1.1.4",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "info.cukes" %% "cucumber-scala" % "1.2.4" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.4" % "test",
  "com.github.tomakehurst" % "wiremock" % "2.1.7" % "test"
)

enablePlugins(CucumberPlugin)

CucumberPlugin.glue := "acceptance"
