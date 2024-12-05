val scala3Version = "3.6.1"

lazy val zioVersion     = "2.1.13"
lazy val refinedVersion = "0.11.2"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "ValidateEverything",
    version      := "0.0.1",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq(
      "-explain"
    ),
    libraryDependencies ++=
      Seq(
        "dev.zio" %% "zio",
        "dev.zio" %% "zio-streams"
      ).map(_ % zioVersion) ++ Seq(
        "dev.zio" %% "zio-test",
        "dev.zio" %% "zio-test-sbt",
        "dev.zio" %% "zio-test-magnolia"
      ).map(
        _ % zioVersion % Test
      ) ++ Seq(
        "eu.timepit" %% "refined",
        "eu.timepit" %% "refined-cats"
      ).map(_ % refinedVersion) ++ Seq(
        // "eu.timepit"    %% "refined-eval"       % "0.11.2", // optional, JVM-only
        // "eu.timepit"    %% "refined-jsonpath"   % "0.11.2", // optional, JVM-only
        // "eu.timepit"    %% "refined-pureconfig" % "0.11.2", // optional, JVM-only
        // "eu.timepit"    %% "refined-scalacheck" % "0.11.2", // optional
        // "eu.timepit"    %% "refined-scalaz"     % "0.11.2", // optional
        // "eu.timepit"    %% "refined-scodec"     % "0.11.2", // optional
        // "eu.timepit"    %% "refined-scopt"      % "0.11.2", // optional
        // "eu.timepit"    %% "refined-shapeless"  % "0.11.2", // optional
        "org.scalameta" %% "munit" % "1.0.2" % Test
      ) ++ Seq(
        "com.lihaoyi"    % "requests_3"  % "0.9.0",
        "org.typelevel" %% "cats-core"   % "2.12.0",
        "dev.zio"       %% "zio-prelude" % "1.0.0-RC35"
      ) ++ Seq(
        "dev.zio" %% "zio-config",
        "dev.zio" %% "zio-config-magnolia",
        "dev.zio" %% "zio-config-typesafe",
        "dev.zio" %% "zio-config-refined"
      ).map(_ % "4.0.2")
  )
