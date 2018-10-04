ThisBuild / scalaVersion := "2.12.6"

lazy val root = (project in file("."))
  .settings(
    name := "BatailleNavale",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"
  )