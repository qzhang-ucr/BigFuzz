package edu.ucla.cs.bigfuzz.sparkprogram

import org.apache.spark.{SparkConf, SparkContext}

object MovieRating {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setMaster("local[*]")
    conf.setAppName("Movie")

    val startTime = System.currentTimeMillis();
    val sc = new SparkContext(conf)

    val text = sc.textFile("/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/movie.csv")
    val rates = text.map { line =>
      val arr = line.split(":")
      val movie_str = arr(0)
      val ratings = arr(1).split(",")(0).split("_")(1)
      (movie_str, ratings.substring(0, 1))
    }
      .map { a =>
        val str = a._1
        (a._1, Integer.parseInt(a._2))
      }
      .filter { v =>
        val tw = v._1
        v._2 > 4
      }.reduceByKey(_ + _)
      .collect
      .foreach(println)

    println("Time: " + (System.currentTimeMillis() - startTime))
  }
}
