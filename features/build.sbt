import Dependencies._

name := "features"

libraryDependencies := Seq(
  scala_lang,
  typesafe_config,
  rest_assured % Test,
  json_path_assert % Test,
  junit_interface % Test,
  cucumber % Test,
  cucumber_junit % Test
)
