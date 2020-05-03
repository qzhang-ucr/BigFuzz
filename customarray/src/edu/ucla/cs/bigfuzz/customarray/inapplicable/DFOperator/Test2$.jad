// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Test2.scala

package edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.rdd.*;
import scala.*;
import scala.math.Ordering;
import scala.reflect.ClassTag$;
import scala.runtime.*;

public final class Test2$
{

    public String f(String s)
    {
        return s;
    }

    public void main(String args[])
    {
        SparkConf conf = (new SparkConf()).setAppName("Scala Toy Example 1: Add Integers").setMaster("local[4]");
        SparkContext sc = new SparkContext(conf);
        sc.textFile("logfile", sc.textFile$default$2());
        RDD text = sc.textFile("/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/income", sc.textFile$default$2());
        RDD data = text.map(new Serializable() {

            public final Tuple3 apply(String s)
            {
                String cols[] = s.split(",");
                return new Tuple3(cols[0], BoxesRunTime.boxToInteger(Integer.parseInt(cols[1])), BoxesRunTime.boxToInteger(Integer.parseInt(cols[2])));
            }

            public final volatile Object apply(Object v1)
            {
                return apply((String)v1);
            }

            public static final long serialVersionUID = 0L;

        }, ClassTag$.MODULE$.apply(scala/Tuple3)).filter(new Serializable() {

            public final boolean apply(Tuple3 s)
            {
                return ((String)s._1()).equals("90024");
            }

            public final Object apply(Object v1)
            {
                return BoxesRunTime.boxToBoolean(apply((Tuple3)v1));
            }

            public static final long serialVersionUID = 0L;

        });
        RDD pair = data.map(new Serializable() {

            public final Tuple2 apply(Tuple3 s)
            {
                return (BoxesRunTime.unboxToInt(s._2()) >= 40) & (BoxesRunTime.unboxToInt(s._2()) <= 65) ? new Tuple2("40-65", s._3()) : (BoxesRunTime.unboxToInt(s._2()) >= 20) & (BoxesRunTime.unboxToInt(s._2()) < 40) ? new Tuple2("20-39", s._3()) : BoxesRunTime.unboxToInt(s._2()) >= 20 ? new Tuple2(">65", s._3()) : new Tuple2("0-19", s._3());
            }

            public final volatile Object apply(Object v1)
            {
                return apply((Tuple3)v1);
            }

            public static final long serialVersionUID = 0L;

        }, ClassTag$.MODULE$.apply(scala.Tuple2));
        RDD$.MODULE$.rddToPairRDDFunctions(RDD$.MODULE$.rddToPairRDDFunctions(RDD$.MODULE$.rddToPairRDDFunctions(pair, ClassTag$.MODULE$.apply(java/lang/String), ClassTag$.MODULE$.Int(), Ordering.String..MODULE$).mapValues(new Serializable() {

            public final Tuple2 apply(int x)
            {
                return new Tuple2.mcII.sp(x, 1);
            }

            public final Object apply(Object v1)
            {
                return apply(BoxesRunTime.unboxToInt(v1));
            }

            public static final long serialVersionUID = 0L;

        }), ClassTag$.MODULE$.apply(java.lang.String), ClassTag$.MODULE$.apply(scala/Tuple2), Ordering.String..MODULE$).reduceByKey(new Serializable() {

            public final Tuple2 apply(Tuple2 x, Tuple2 y)
            {
                return new Tuple2.mcII.sp(x._1$mcI$sp() + y._1$mcI$sp(), x._2$mcI$sp() + y._2$mcI$sp());
            }

            public final volatile Object apply(Object v1, Object v2)
            {
                return apply((Tuple2)v1, (Tuple2)v2);
            }

            public static final long serialVersionUID = 0L;

        }), ClassTag$.MODULE$.apply(java/lang/String), ClassTag$.MODULE$.apply(scala/Tuple2), Ordering.String..MODULE$).mapValues(new Serializable() {

            public final Tuple2 apply(Tuple2 x)
            {
                return new Tuple2.mcID.sp(x._2$mcI$sp(), (double)x._1$mcI$sp() / (double)x._2$mcI$sp());
            }

            public final volatile Object apply(Object v1)
            {
                return apply((Tuple2)v1);
            }

            public static final long serialVersionUID = 0L;

        }).foreach(new Serializable() {

            public final void apply(Object x)
            {
                Predef$.MODULE$.println(x);
            }

            public final volatile Object apply(Object v1)
            {
                apply(v1);
                return BoxedUnit.UNIT;
            }

            public static final long serialVersionUID = 0L;

        });
        BoxedUnit sum = BoxedUnit.UNIT;
    }

    private Test2$()
    {
    }

    public static final Test2$ MODULE$ = this;

    static
    {
        new Test2$();
    }
}