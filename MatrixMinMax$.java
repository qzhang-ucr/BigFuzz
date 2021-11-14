// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MatrixMinMax.scala

package main.scala.examples.benchmarks.input_reduction_benchmarks;

import main.scala.examples.benchmarks.AggregationFunctions$;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import main.scala.provenance.data.Provenance$;
import main.scala.provenance.rdd.PairProvenanceRDD;
import main.scala.provenance.rdd.ProvenanceRDD;
import scala.*;
import scala.collection.immutable.*;
import scala.collection.mutable.ArrayOps;
import scala.reflect.ClassTag$;
import scala.runtime.*;
import sparkwrapper.SparkContextWithDP;
import main.scala.symbolicprimitives.SymFloat;
import main.scala.symbolicprimitives.SymString;

public final class MatrixMinMax$
{

    public void main(String args[])
    {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[6]");
        sparkConf.setAppName("Column Provenance Test").set("spark.executor.memory", "2g");
        String dataset1 = "datasets/colprov_matrix1";
        SparkContext ctx = new SparkContext(sparkConf);
        ctx.setLogLevel("ERROR");
        Provenance$.MODULE$.setProvenanceType("dual");
        SparkContextWithDP scdp = new SparkContextWithDP(ctx);
        ProvenanceRDD data1 = scdp.textFileProv(dataset1, new Serializable() {

            public final String[] apply(String x)
            {
                return (new StringOps(Predef$.MODULE$.augmentString(x))).split(',');
            }

            public final volatile Object apply(Object v1)
            {
                return apply((String)v1);
            }

            public static final long serialVersionUID = 0L;

        });
        int dummy = 666;
        ProvenanceRDD result0 = data1.flatMap(new Serializable(dummy) {

            public final List apply(SymString r[])
            {
                SymFloat int_row[] = (SymFloat[])Predef$.MODULE$.refArrayOps((Object[])Predef$.MODULE$.refArrayOps((Object[])r).map(new Serializable() {

                    public final SymFloat apply(SymString x$1)
                    {
                        return x$1.toFloat();
                    }

                    public final volatile Object apply(Object v1)
                    {
                        return apply((SymString)v1);
                    }

                    public static final long serialVersionUID = 0L;

                }, Array$.MODULE$.canBuildFrom(ClassTag$.MODULE$.apply(main.scala.symbolicprimitives /SymFloat)))).tail();
                SymFloat min = (SymFloat)Predef$.MODULE$.refArrayOps((Object[])int_row).reduce(new Serializable() {

                    public final SymFloat apply(SymFloat acc, SymFloat e)
                    {
                        return acc.$less(e) ? acc : e;
                    }

                    public final volatile Object apply(Object v1, Object v2)
                    {
                        return apply((SymFloat)v1, (SymFloat)v2);
                    }

                    public static final long serialVersionUID = 0L;

                });
                SymFloat max = (SymFloat)Predef$.MODULE$.refArrayOps((Object[])int_row).reduce(new Serializable() {

                    public final SymFloat apply(SymFloat acc, SymFloat e)
                    {
                        return acc.$greater(e) ? acc : e;
                    }

                    public final volatile Object apply(Object v1, Object v2)
                    {
                        return apply((SymFloat)v1, (SymFloat)v2);
                    }

                    public static final long serialVersionUID = 0L;

                });
                return List$.MODULE$.apply(Predef$.MODULE$.wrapRefArray((Object[])(new Tuple2[] {
                    new Tuple2(BoxesRunTime.boxToInteger(dummy$1), min), new Tuple2(BoxesRunTime.boxToInteger(dummy$1), max)
                })));
            }

            public final volatile Object apply(Object v1)
            {
                return apply((SymString[])v1);
            }

            public static final long serialVersionUID = 0L;
            private final int dummy$1;

            public 
            {
                this.dummy$1 = dummy$1;
                super();
            }
        }, data1.flatMap$default$2(), ClassTag$.MODULE$.apply(scala/Tuple2));
        PairProvenanceRDD result1 = AggregationFunctions$.MODULE$.minMaxDeltaByKey(result0, ClassTag$.MODULE$.Int(), scala.Predef.DummyImplicit..MODULE$.dummyImplicit());
        Predef$.MODULE$.refArrayOps((Object[])result1.collectWithProvenance()).foreach(new Serializable() {

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
    }

    private MatrixMinMax$()
    {
    }

    public static final MatrixMinMax$ MODULE$ = this;

    static 
    {
        new MatrixMinMax$();
    }
}
