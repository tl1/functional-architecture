import sbt._

object Dependencies {

  val akka_version = "2.5.19"
  val jackson_version = "2.8.11"

  // Misc --------------------------------------------------------------------------------------------------------------
  
  lazy val inject = "javax.inject" % "javax.inject" % "1"
  lazy val typesafe_config = "com.typesafe" % "config" % "1.3.4"
  lazy val grizzled = "org.clapper" %% "grizzled-slf4j" % "1.3.3"
  lazy val reactive_streams = "org.reactivestreams" % "reactive-streams" % "1.0.2"
  
  // Test --------------------------------------------------------------------------------------------------------------
  
  lazy val scala_test = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val akka_stream_testkit = "com.typesafe.akka" %% "akka-stream-testkit" % akka_version
  lazy val easy_mock = "org.easymock" % "easymock" % "4.0.2"
  lazy val scala_lang = "org.scala-lang" % "scala-library" % "2.12.8"
  lazy val junit_interface = "com.novocode" % "junit-interface" % "0.11"
  lazy val cucumber = "io.cucumber" %% "cucumber-scala" % "4.3.0" exclude("junit", "junit-dep")
  lazy val cucumber_junit = "io.cucumber" % "cucumber-junit" % "4.3.0"
  lazy val rest_assured = "io.rest-assured" % "rest-assured" % "4.0.0"
  lazy val json_path_assert = "com.jayway.jsonpath" % "json-path-assert" % "2.2.0"
  
  // Overrides ---------------------------------------------------------------------------------------------------------
  
  lazy val scala_parser_combinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.7"
  lazy val akka_stream = "com.typesafe.akka" %% "akka-stream" % akka_version
  lazy val akka_actor = "com.typesafe.akka" %% "akka-actor" % akka_version
  lazy val guava = "com.google.guava" % "guava" % "23.6.1-jre"
  lazy val jackson_databind = "com.fasterxml.jackson.core" % "jackson-databind" % jackson_version
  lazy val jackson_core = "com.fasterxml.jackson.core" % "jackson-core" % jackson_version
  lazy val jackson_annotations = "com.fasterxml.jackson.core" % "jackson-annotations" % jackson_version

}
