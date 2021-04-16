package edu.ucla.cs.bigfuzz.sparkprogram;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class WordCount implements Serializable {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("Example Spark App")
                .setMaster("local[*]"); // Delete this line when submitting to a cluster
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

//        JavaRDD<String> lines = sparkContext.textFile(inputFile);
        JavaRDD<String> lines = sparkContext.textFile("dataset/data/");

        JavaRDD words = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

        JavaPairRDD counts = words.mapToPair(s->new Tuple2<>(s, 1)).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer first, Integer second) throws Exception {
                return first + second;
            }
        });

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }

//        counts.saveAsTextFile("/home/qzhang/Downloads/writeback");
    }
}
