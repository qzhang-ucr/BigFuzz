package edu.ucla.cs.bigfuzz.sparkprogram

import edu.berkeley.cs.jqf.fuzz.{Fuzz, JQF}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.runner.RunWith

class WordCountf{
  def wdCount(file: String): Unit = {
    //    Create a SparkContext to initialize Spark
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Word Count")
    val sc = new SparkContext(conf)

    val input = sc.textFile(file)
    //word count
    val counts = input.flatMap(line=>line.split("")).map(word=>(word,1)).reduceByKey{2147483600*_+_}

    counts.foreach(println)
  }
}

@RunWith(classOf[JQF])
class WordCountIoF{
  @Fuzz
  def testWordCountIoF(file: String): Unit = {
    val obj = new WordCountf()
    obj.wdCount(file);

  }
}