import org.apache.spark.{SparkConf, SparkContext}

class SparkWordCount {
  def wdCount(fileName: String): Unit = {

    //Create a SparkContext to initialize Spark
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Word Count")
    val sc = new SparkContext(conf)

    // Load the text into a Spark RDD, which is a distributed representation of each line of text
    //val textFile = sc.textFile("/home/qzhang/Downloads/input")
    val textFile = sc.textFile(fileName)

    //word count
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(println)
    System.out.println("Total words: " + counts.count());
    // counts.saveAsTextFile("/home/qzhang/Downloads/writeback");
  }
}


object WordCount {
//    def main(args: Array[String]): Unit = {
//      println("HelloWorld!")
//    }
  def main(args: Array[String]): Unit = {
     val obj = new SparkWordCount()
     obj.wdCount("/home/qzhang/Downloads/input");
  }
}
