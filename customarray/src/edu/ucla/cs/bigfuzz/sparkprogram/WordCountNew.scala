package edu.ucla.cs.bigfuzz.sparkprogram

import org.apache.spark.{SparkConf, SparkContext}

object WordCountNew{

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Student")
    val sc = new SparkContext(conf)

    val textFile = sc.textFile("README.md")

    val wordCounts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_+_)
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

