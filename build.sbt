name := "scala-drools"

version := "1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases",
  "sonatype-public" at "https://oss.sonatype.org/content/groups/public"
)

val drools = Seq(
  "drools-compiler",
  "drools-core",
  "drools-jsr94",
  "drools-decisiontables",
  "knowledge-api"
).map("org.drools" % _ % "6.1.0.Final")

val additionalDroolsDependencies = Seq(
  "com.sun.xml.bind" % "jaxb-xjc" % "2.2.4-1",
  "com.thoughtworks.xstream" % "xstream" % "1.4.2"
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
) ++ drools ++ additionalDroolsDependencies

