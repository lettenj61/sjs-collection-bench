lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.lettenj61",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "sjs-collection-benchmark",
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-benchmark" %%% "benchmark" % "0.2.4"
    )
  )
  .enablePlugins(ScalaJSPlugin)

