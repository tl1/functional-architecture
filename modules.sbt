lazy val features = project in file("./features")

lazy val bookings = project in file("./bookings")

lazy val api = (project in file("./api"))
  .enablePlugins(PlayScala)
  .dependsOn(bookings)

lazy val root = (project in file("."))
  .aggregate(
    bookings,
    api
  )
