import de.heikoseeberger.sbtheader.CommentStyleMapping
import de.heikoseeberger.sbtheader.license.Apache2_0

name := "stub-dsl"

version := "0.1.0"

organization := "alvarovg"

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

enablePlugins(AutomateHeaderPlugin)

CucumberPlugin.glue := "acceptance"

headers := CommentStyleMapping.createFrom(Apache2_0, "2015", "Alvaro Vilaplana Garcia")

licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))

bintrayPackageLabels := Seq("wiremock", "stub", "dsl")

