

import org.apache.spark.{SparkConf, SparkContext}

object IncomeAggregate  {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
//    conf.setMaster("local[6]")
    conf.setAppName("Income")

    val startTime = System.currentTimeMillis();
    val sc = new SparkContext(conf)

    val text = sc.textFile(args(0));

//    val text = sc.textFile("/Users/zhuhaichao/Documents/Workspace/github/BigFuzz/dataset/salary1.csv");
//    val text = sc.textFile("/home/qzhang/Programs/BigFuzz/dataset/salary1.csv")

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

//    (90024, 40, 5000)
//   map s1, s2, s3 (90024, 40, 5000)
//   filter  s1 s2 s3 (90024 xxxx)
//    map (40-50, s3), (0-19, s3)
//    mapvalues (label, (s3, 1))
//    reducebykey (label (s3, +))
//    mapvalue (label, s3/+)


    //    try{
    //      val sum = text.map{
    //        line =>
    //          if (line.substring(0, 1).equals("$")) {
    //            val i = line.substring(1)
    //            i
    //          } else {
    //            line
    //          }
    //      }
    //        .map( p => Integer.parseInt(p))
    //        .filter( r => r < 300)
    //        .reduce(_ + _)
    //      println(sum)
    //    }
    //    catch {
    //      case e : Exception =>
    //        e.printStackTrace()
    //    }

    println("Time: " + (System.currentTimeMillis() - startTime))
  }
}
