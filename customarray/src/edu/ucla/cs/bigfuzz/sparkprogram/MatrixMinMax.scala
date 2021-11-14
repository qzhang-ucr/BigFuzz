package main.scala.examples.benchmarks.input_reduction_benchmarks

import org.apache.spark.{SparkConf, SparkContext}
import provenance.data.Provenance
import sparkwrapper.SparkContextWithDP
import main.scala.examples.benchmarks.AggregationFunctions
import symbolicprimitives.{SymFloat, SymInt}
import symbolicprimitives.SymImplicits._
import symbolicprimitives.Utils


object MatrixMinMax {
  def main(args: Array[String]): Unit = {
    //set up spark configuration
    val sparkConf = new SparkConf()
    sparkConf.setMaster("local[6]")
    sparkConf.setAppName("Column Provenance Test").set("spark.executor.memory", "2g")
    var dataset1 = "datasets/colprov_matrix1"
//    var dataset2 = "datasets/colprov_matrix2"

    val ctx = new SparkContext(sparkConf) //set up lineage context and start capture lineage
    ctx.setLogLevel("ERROR")
    Provenance.setProvenanceType("dual")
    val scdp = new SparkContextWithDP(ctx)
    val data1 = scdp.textFileProv(dataset1, createCol = {x: String => x.split(',')})
//    val data2 = scdp.textFileProv(dataset1, createCol = {x: String => x.split(',')})
//  ---------------------------------------------------------------------------------------
    val dummy = 666
    val flattened = data1
      .flatMap{ r =>
        val int_row = r.map(_.toFloat).tail
        val min = int_row.reduce((acc, e) => if (acc < e) acc else e)
        val max = int_row.reduce((acc, e) => if (acc > e) acc else e)
        List((dummy, min), (dummy, max))
      }
    val agg = AggregationFunctions.minMaxDeltaByKey(flattened)
    agg.collectWithProvenance().foreach(println)
  }
}