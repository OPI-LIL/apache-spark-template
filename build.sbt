
import sbt._
import TaskProvider._
import Def.Initialize
import complete.DefaultParsers._


lazy val commonSettings = Seq(
  organization := "com.opi.lil",
  version := "1.2",
  scalaVersion := "2.10.4",           // desired scala version
  remote := "user@1host",             // host and user name
  remoteFolder := "/home/spark/dev/", // dest directory of jar files
  defaultClass := "MainApp",
  deploy := deployImpl.value,
  submit := {
    (TaskProvider.`deploy` in Compile).value   // depends on deploy task
    val args: Seq[String] = spaceDelimited("<arg>").parsed        
    val className = if (args==Nil) defaultClass.value else args.head
    val jar = new JarData(name.value, version.value, scalaVersion.value)              
    Process(s"cmd /C scripts\\run ${remote.value} ${remoteFolder.value} ${jar.fileName()} $className").!    
  }
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
  	name := "spark-template",  
    libraryDependencies ++= Seq(
      "org.apache.spark"  % "spark-core_2.10"  % "1.2.0", 
      "org.apache.spark"  % "spark-mllib_2.10" % "1.2.0",
      "org.apache.hadoop" % "hadoop-client" % "2.5.0",
      "com.typesafe" % "config" % "1.0.0")  
  )