/* SimpleApp.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.math.random


object MainApp {
  def main(args: Array[String]) {

    println("[info] Calculating Pi...")

    // Fill this with appropriate data
    val masterIP =  "0.0.0.0"  
    val masterPort = "7077"    
    val maxCores = "20"        
    val executorMemory = "4G" 
    val driverMemory = "512m"

    val conf = new SparkConf()
      .setMaster(s"spark://$masterIP:$masterPort")
      .setAppName("Main Application")      
      .set("spark.local.ip", masterIP)
      .set("spark.driver.host", masterIP)
      .set("spark.cores.max", maxCores)
      .set("spark.executor.memory", executorMemory)
      .set("spark.driver.memory", driverMemory)
    
    val spark = new SparkContext(conf)


    val slices = if (args.length > 0) args(0).toInt else 10
    val n = 10^6 * slices
    val count = spark.parallelize(1 to n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y < 1) 1 else 0
    }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    spark.stop()
  }
}