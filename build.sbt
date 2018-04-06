lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "S4N",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT",
      javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
      // For project with only Java sources. In order to compile Scala sources, remove the following two lines.
    )),
    name := "CorrientazoADomicilio",
    libraryDependencies ++= Seq(
      "io.vavr" % "vavr" % "0.9.2",
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.powermock" % "powermock-module-junit4" % "1.6.4",
      "org.powermock" % "powermock-api-mockito" % "1.6.4"
    )
  )