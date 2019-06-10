import Dependencies._

name := "functional-architecture"

inThisBuild(
  List(
    version := "1",
    scalaVersion := "2.12.8",
    dependencyOverrides := Seq(
      scala_parser_combinators,
      akka_stream,
      akka_actor,
      guava,
      jackson_core,
      jackson_databind,
      jackson_annotations
    )
  )
)

