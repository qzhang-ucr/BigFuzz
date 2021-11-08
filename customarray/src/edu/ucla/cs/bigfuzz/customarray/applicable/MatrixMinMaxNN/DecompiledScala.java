//// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.geocities.com/kpdus/jad.html
//// Decompiler options: packimports(3)
//// Source File Name:   MatrixMinMaxNN.scala
//
//package examples.benchmarks.input_reduction_benchmarks;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.rdd.*;
//import scala.*;
//import scala.collection.immutable.StringOps;
//import scala.collection.mutable.ArrayOps;
//import scala.math.Ordering;
//import scala.reflect.ClassTag$;
//import scala.runtime.*;
//
//public final class MatrixMinMaxNN$
//{
//
//    public void main(String args[])
//    {
//        SparkConf sparkConf = new SparkConf();
//        sparkConf.setMaster("local[6]");
//        sparkConf.setAppName("Column Provenance Test").set("spark.executor.memory", "2g");
//        String dataset1 = "datasets/colprov_matrix1";
//        SparkContext ctx = new SparkContext(sparkConf);
//        ctx.setLogLevel("ERROR");
//        RDD data1 = ctx.textFile(dataset1, ctx.textFile$default$2());
//        int dummyKey = 555;
//        RDD flat = data1.flatMap(new Serializable() {
//
//            public final ArrayOps apply(String r)
//            {
//                return Predef$.MODULE$.refArrayOps((Object[])Predef$.MODULE$.refArrayOps((Object[])r.split(",")).tail());
//            }
//
//            public final volatile Object apply(Object v1)
//            {
//                return apply((String)v1);
//            }
//
//            public static final long serialVersionUID = 0L;
//
//        }, ClassTag$.MODULE$.apply(java/lang/String)).map(new Serializable(dummyKey) {
//
//            public final Tuple2 apply(String s)
//            {
//                return new Tuple2(BoxesRunTime.boxToInteger(dummyKey$1), BoxesRunTime.boxToFloat((new StringOps(Predef$.MODULE$.augmentString(s))).toFloat()));
//            }
//
//            public final volatile Object apply(Object v1)
//            {
//                return apply((String)v1);
//            }
//
//            public static final long serialVersionUID = 0L;
//            private final int dummyKey$1;
//
//            public
//            {
//                this.dummyKey$1 = dummyKey$1;
//                super();
//            }
//        }, ClassTag$.MODULE$.apply(scala/Tuple2));
//        float min = BoxesRunTime.unboxToFloat(((Tuple2[])RDD$.MODULE$.rddToPairRDDFunctions(flat, ClassTag$.MODULE$.Int(), ClassTag$.MODULE$.Float(), scala.math.Ordering.Int..MODULE$).reduceByKey(new Serializable() {
//
//            public final float apply(float acc, float e)
//            {
//                return acc >= e ? e : acc;
//            }
//
//            public final volatile Object apply(Object v1, Object v2)
//            {
//                return BoxesRunTime.boxToFloat(apply(BoxesRunTime.unboxToFloat(v1), BoxesRunTime.unboxToFloat(v2)));
//            }
//
//            public static final long serialVersionUID = 0L;
//
//        }).collect())[0]._2());
//        float max = BoxesRunTime.unboxToFloat(((Tuple2[])RDD$.MODULE$.rddToPairRDDFunctions(flat, ClassTag$.MODULE$.Int(), ClassTag$.MODULE$.Float(), scala.math.Ordering.Int..MODULE$).reduceByKey(new Serializable() {
//
//            public final float apply(float acc, float e)
//            {
//                return acc <= e ? e : acc;
//            }
//
//            public final volatile Object apply(Object v1, Object v2)
//            {
//                return BoxesRunTime.boxToFloat(apply(BoxesRunTime.unboxToFloat(v1), BoxesRunTime.unboxToFloat(v2)));
//            }
//
//            public static final long serialVersionUID = 0L;
//
//        }).collect())[0]._2());
//        float diff = max - min;
//        Predef$.MODULE$.println(BoxesRunTime.boxToFloat(diff));
//    }
//
//    private MatrixMinMaxNN$()
//    {
//    }
//
//    public static final MatrixMinMaxNN$ MODULE$ = this;
//
//    static
//    {
//        new MatrixMinMaxNN$();
//    }
//}
