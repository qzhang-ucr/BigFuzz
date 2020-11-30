

import org.apache.spark.{SparkConf, SparkContext}

object IncomeAggregate  {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
//    conf.setMaster("local[6]")
    conf.setAppName("Income")

    val startTime = System.currentTimeMillis();
    val sc = new SparkContext(conf)

    val text = sc.textFile(args(0));

    val data = text.map {
      s =>
        val cols = s.split(",")
        (cols(0), Integer.parseInt(cols(1)), Integer.parseInt(cols(2)))
    }.filter( s => s._1.equals("90024"))

    val pair = data.map {
      s =>
        // Checking if age is within certain range
        if (s._2 >= 40 & s._2 <= 65) {
          ("40-65", s._3)
        } else if (s._2 >= 20 & s._2 < 40) {
          ("20-39", s._3)
        } else if (s._2 < 20){
          ("0-19", s._3)
        } else {
          (">65", s._3)
        }
    }

    val sum = pair.mapValues( x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
      .mapValues(x => (x._2, x._1.toDouble / x._2.toDouble))
      .foreach(println)

    println("Time: " + (System.currentTimeMillis() - startTime))
  }
}
