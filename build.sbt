name := """play-websockets-test"""

scalaVersion := "2.12.11"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """websocket-test-app""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )