package edu.ucla.cs.bigfuzz.sparkprogram

import org.apache.spark.{SparkConf, SparkContext}

object MatrixMinMaxNN {

  def main(args: Array[String]): Unit = {
    //set up spark configuration
    val sparkConf = new SparkConf()
    sparkConf.setMaster("local[6]")
    sparkConf.setAppName("Column Provenance Test").set("spark.executor.memory", "2g")
    var dataset1 = "datasets/colprov_matrix1"
//    var dataset2 = "datasets/colprov_matrix2"

    val ctx = new SparkContext(sparkConf) //set up lineage context and start capture lineage
    ctx.setLogLevel("ERROR")
    val data1 = ctx.textFile(dataset1)
//    val data2 = scdp.textFileProv(dataset1, createCol = {x: String => x.split(',')})
//  ---------------------------------------------------------------------------------------

    val dummyKey = 555;
    val flat = data1.flatMap{r => r.split(",").tail}.map{s => (dummyKey, s.toFloat)}
    val min = flat.reduceByKey((acc, e) => if (acc < e) acc else e).collect()(0)._2
    val max = flat.reduceByKey((acc, e) => if (acc > e) acc else e).collect()(0)._2
    val diff = max - min;
    println(diff)
//    Utils.retrieveProvenance(diff.getProvenance()).foreach(println)
  }
}