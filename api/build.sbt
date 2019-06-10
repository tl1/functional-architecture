import Dependencies._

name := "api"

libraryDependencies ++= Seq(
  guice,
  scala_test % Test
)