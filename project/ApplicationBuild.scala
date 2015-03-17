// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {

  import org.scalastyle.sbt.ScalastylePlugin.{Settings => scalastyleSettings}

  object Versions {
    val kafka = "0.8.2.1"
    val spark = "1.3.0"
  }

  val customScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked")

  val customResolvers = Seq(
    Classpaths.sbtPluginReleases,
    Classpaths.typesafeReleases,
    "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
  )

  val customLibraryDependencies = Seq(
    "org.apache.kafka" %% "kafka" % Versions.kafka,
    "org.apache.kafka" % "kafka-clients" % Versions.kafka,

    "org.apache.spark" %% "spark-core" % Versions.spark,
    "org.apache.spark" %% "spark-streaming" % Versions.spark,
    "org.apache.spark" %% "spark-streaming-kafka" % Versions.spark,

    "org.slf4j" % "slf4j-api" % "1.7.10",
    "ch.qos.logback" % "logback-classic" % "1.1.2"
  ).map(_.exclude(
    "org.slf4j", "slf4j-log4j12"
  ))

  lazy val main = Project(
    id = "example-kafka",
    base = file("."),
    settings = Defaults.coreDefaultSettings ++ Seq(
      version := "1.0",
      organization := "http://mkuthan.github.io/",
      scalaVersion := "2.10.4",
      scalacOptions ++= customScalacOptions,
      resolvers ++= customResolvers,
      libraryDependencies ++= customLibraryDependencies,
      parallelExecution in Test := false,
      fork in Test := true
    ) ++ scalastyleSettings
  )

}
