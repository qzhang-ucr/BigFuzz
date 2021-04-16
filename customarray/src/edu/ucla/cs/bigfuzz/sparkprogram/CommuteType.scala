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

    val data_trip = sc.textFile("dataset/trips.csv")
    val data_zipcode = sc.textFile("dataset/zipcode.csv")

    val trips = data_trip.map {
      s =>
        val cols = s.split(",")
        (cols(1), Integer.parseInt(cols(3)) / Integer.parseInt(cols(4)))
    }

    trips.foreach(println)

    val locations = data_zipcode.map {
      s =>
        val cols = s.split(",")
        (cols(0), cols(1))
    }
      .filter( s => s._2.equals("Palms"))

    locations.foreach(println)

    val joined = trips.join(locations)
    joined.foreach(println)

    joined.map { s =>
      // Checking if speed is < 25mi/hr
      if (log10(s._2._1) > 40) {
        ("car", 1)
      } else if (log10(s._2._1) > 15) {
        ("public", 1)
      } else {
        ("walk", 1)
      }
    }
      .reduceByKey(_ + _)
      .collect
      .foreach(println)
    println("Job Finished")

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

