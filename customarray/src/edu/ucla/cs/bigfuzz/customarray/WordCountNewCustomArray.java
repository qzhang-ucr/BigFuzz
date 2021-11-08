package edu.ucla.cs.bigfuzz.customarray;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;

import java.util.*;public class WordCountNewCustomArray {

    private ArrayList<Object> list;

    public void WordCountNewCustomArray() {

    }
 public static  Object[] FlatMap1(String line){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));return flatMap1.apply( line);

}
 public static  map2 Map1(String s){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));return map2.apply( s);

}
 public static  int ReduceByKey1( int[] a){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));return reduceByKey1.applyReduce( a);

}
}