import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.3.1")
addCompilerPlugin("org.typelevel"  %% "kind-projector"     % "0.10.3")
addCompilerPlugin("org.augustjune" %% "context-applied"    % "0.1.2")

fork in run := true

lazy val catsVersion   = "2.1.1"
lazy val circeVersion  = "0.13.0"
lazy val http4sVersion = "0.21.3"

lazy val root = (project in file("."))
  .settings(
    name := "rest-in-scala",
    libraryDependencies ++= Seq(
      
      "org.typelevel" %% "cats-core"           % catsVersion withSources() withJavadoc(),
      "org.typelevel" %% "cats-effect"         % catsVersion withSources() withJavadoc(),
      "io.circe"      %% "circe-core"          % circeVersion withSources() withJavadoc(),
      "io.circe"      %% "circe-core"          % circeVersion withSources() withJavadoc(),
      "io.circe"      %% "circe-generic"       % circeVersion withSources() withJavadoc(),
      "io.circe"      %% "circe-parser"        % circeVersion withSources() withJavadoc(),
      "org.http4s"    %% "http4s-circe"        % http4sVersion withSources() withJavadoc(),
      "org.http4s"    %% "http4s-dsl"          % http4sVersion withSources() withJavadoc(),
      "org.http4s"    %% "http4s-blaze-client" % http4sVersion withSources() withJavadoc(),
      scalaTest % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
