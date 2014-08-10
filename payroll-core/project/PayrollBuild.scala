import sbt._
import Keys._

object PayrollBuild extends Build {
  val Organization = "anova"
  val Name = "payroll-core"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
  val ScalaTestVersion = "2.2.1"

  lazy val project = Project(
    Name,
    file("."),
    settings =
      Defaults.defaultSettings ++
        Seq(
          organization := Organization,
          name := Name,
          version := Version,
          scalaVersion := ScalaVersion,
          javacOptions ++= Seq("-source", "1.7"),
          libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest" % ScalaTestVersion % "test"
          )
        )
  )
}