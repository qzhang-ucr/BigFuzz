package edu.ucla.cs.bigfuzz.customarray.applicable.OldMatrixMinMaxNN;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.dataflow.FlatMapEvent;
import edu.ucla.cs.bigfuzz.dataflow.MapEvent;
import edu.ucla.cs.bigfuzz.dataflow.ReduceByKeyEvent;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;

import java.util.ArrayList;

public class MatrixMinMaxNNCustomArray {

    private ArrayList<Object> list;

    public void MatrixMinMaxNNCustomArray() {

    }
    public static  ArrayList<String[]> FlatMap1(ArrayList<String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));
        ArrayList<String[]> ans = new ArrayList<>();
        for ( String results: result){ans.add(flatMap1.apply(results));}


        return ans;
    }
    public static ArrayList<Float> Map1(ArrayList<String[]> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList<Float> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
        for ( String[] results: result){for(String R: results) {ans.add( map1.apply(R));}}
        return ans;
    }

    public static float ReduceByKey1(ArrayList<Float> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));
        float min = Float.POSITIVE_INFINITY;
        for(float r : result){
            min = reduceByKey1.applyReduce(min, r);
        }
        return min;
    }

    public static float ReduceByKey2(ArrayList<Float> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));
        float max = Float.NEGATIVE_INFINITY;
        for(float r : result){
            max = reduceByKey2.applyReduce(max, r);
        }
        return max;
    }
}
