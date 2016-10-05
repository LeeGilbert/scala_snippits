lazy val root = (project in file(".")).
  settings(
    name := "snippits",
    version := "1.0",
    scalaVersion := "2.11.8"
  )

libraryDependencies ++= Seq(
  "org.jfree" % "jfreechart" % "1.0.19",
  "org.jfree" % "jfreesvg" % "3.1"
)


resolvers += "jFrog" at "http://revgit:9091/artifactory/remote-repos"