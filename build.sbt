import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    resolvers += "Sonatype OSS Snapshots" at
      "https://oss.sonatype.org/content/repositories/snapshots",
    name := "Purely Functional Data Structures",
    scalacOptions in Test ++= Seq("-Yrangepos"),
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.0.2" % "test"),
    libraryDependencies ++= Seq("org.specs2" %% "specs2-scalacheck" % "4.0.2" % "test"),
    libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.8.2",
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    parallelExecution in Test := false
  )
