import Dependencies._

name := "bookings"

libraryDependencies ++= Seq(
  reactive_streams,
  scala_test % Test
)
