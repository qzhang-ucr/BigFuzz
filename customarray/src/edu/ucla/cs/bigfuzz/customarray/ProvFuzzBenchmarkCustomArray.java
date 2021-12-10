package edu.ucla.cs.bigfuzz.customarray;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.customarray.applicable.ProvFuzzBenchmark.filter2;
import edu.ucla.cs.bigfuzz.customarray.applicable.ProvFuzzBenchmark.map1;
import edu.ucla.cs.bigfuzz.customarray.applicable.ProvFuzzBenchmark.map3;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;public class ProvFuzzBenchmarkCustomArray {

    private ArrayList<Object> list;

    public void ProvFuzzBenchmarkCustomArray() {

    }
 public static  ArrayList<String[]> Map1(ArrayList<String> rows){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
        ArrayList<String[]> ans = new ArrayList<>();
        for(String row : rows) {
            ans.add(map1.apply(row));
        }
        return ans;
}
 public static  ArrayList<String> Map2(ArrayList<String[]> x){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
        ArrayList<String> ans = new ArrayList<>();
        for(String[] results : x) {
             ans.add(map3.apply(results));
        }
        return ans;
}
 public static ArrayList<String> Filter1(ArrayList<String> x){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,0));

     ArrayList<String> ans = new ArrayList<>();
     int arm = 1;
     for ( String results: x){
         if (filter2.apply(results))
             ans.add(results);
     }
     if (ans.isEmpty()) {arm=0;}
     TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,arm));
     return ans;

}
}