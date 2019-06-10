import Dependencies._

name := "reservations"

libraryDependencies ++= Seq(
  reactive_streams,
  akka_stream,
  akka_stream_testkit % Test,
  scala_test % Test
)
