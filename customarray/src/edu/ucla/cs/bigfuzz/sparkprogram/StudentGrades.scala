package edu.ucla.cs.bigfuzz.sparkprogram

import org.apache.spark.{SparkConf, SparkContext}

object StudentGrades{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Student")

    val startTime = System.currentTimeMillis();
    val sc = new SparkContext(conf)
    val text = sc.textFile("home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/studentGrades/part-00000")

    val map1 = text.flatMap { line =>
      val arr = line.split(",")
      arr
    }
      .map { l=>
        val a = l.split(":")
        (a(0), Integer.parseInt(a(1)))
      }
      .map { a =>
        if (a._2 > 40)
          (a._1.concat(" Pass"), 1)
        else
          (a._1.concat(" Fail"), 1)
      }
      .reduceByKey(_+_)
      .filter { v =>
        val tw = v._1
        v._2 > 40
      }
      //    flatMap(l => l.split("\n")).flatMap{ line =>
//      val arr = line.split(",")
//      arr
//    }
//      .map{  s =>
//        val a = s.split(":")
//        (a(0) , a(1).toInt)
//      }
//      .map { a =>
//        if (a._2 > 40)
//          (a._1 + " Pass", 1)
//        else
//          (a._1 + " Fail", 1)
//      }
//      .reduceByKey(_ + _)
//      .filter(v => v._2 > 1)
      .collect
      .foreach(println)
    println("Time: " + (System.currentTimeMillis() - startTime))
  }
}


/***
Big Test Conf
filter1 > "",1
map3> "",1
map4 > "CS:123"
reduceByKey2 > {1,2,3,4}
flatMap5 > "a,a"
DAG >filter1-reduceByKey2:reduceByKey2-map3:map3-map4:map4-flatMap5
  */

