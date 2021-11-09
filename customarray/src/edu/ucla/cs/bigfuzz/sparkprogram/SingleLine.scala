package edu.ucla.cs.bigfuzz.sparkprogram

import org.apache.spark.{SparkConf, SparkContext}

import math.log10

object CommuteType{

  def main(args: Array[String]) {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Commute")

    val startTime = System.currentTimeMillis();
    val sc = new SparkContext(conf)

    val data_trip = sc.textFile("/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/trips.csv")
    val data_zipcode = sc.textFile("/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/zipcode.csv")

    val locations = data_zipcode.map { s => s.split(",").filter( t => t.equals("Palms")) }

    println("Time: " + (System.currentTimeMillis() - startTime))
  }
}


/* *
  *
  * map1>","
map3>","
filter2>"",""
map5>"1" , 2, "1"
reduceByKey4> {1,2,3,4}
K_BOUND>2
DAG >reduceByKey4-map5:map5-join:join-map1,filter2:filter2-map3

 */

