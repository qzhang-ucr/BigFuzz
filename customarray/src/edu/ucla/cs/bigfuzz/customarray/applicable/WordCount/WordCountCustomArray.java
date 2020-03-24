package edu.ucla.cs.bigfuzz.customarray.applicable.WordCount;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;

import java.util.*;public class WordCountCustomArray {

    private ArrayList<Object> list;

    public void WordCountCustomArray() {

    }
 public static ArrayList< String[]> FlatMap1(ArrayList<String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< String[]> ans = new ArrayList<>();
        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));
        for (String results: result){ans.add( flatMap3.apply( results));}
        return ans;
}
 public static ArrayList< mapToPair2> MapToPair1(ArrayList< String[]> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< mapToPair2> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapToPairEvent(iid, method, callersLineNumber));
for ( String[] results: result){for(String R: results) {ans.add( mapToPair2.apply( R));}}
return ans;
}
 public static ArrayList< mapToPair2> ReduceByKey1(ArrayList< mapToPair2> result) throws Exception {
     int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

     int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
     MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method
     TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));

     ArrayList<mapToPair2> ans =new ArrayList<>();
     int[][] array;  //prepare for reduce by key, array[][] records number list
     int[] num; // num[] records how many same item for one specific item
     num = new int [result.size()+1];
     array = new int [result.size()+1][result.size()+1];
     ArrayList<mapToPair2> results55 = new ArrayList<>();
     for (mapToPair2 result1: result){
         int a = 0; //flag
         for (mapToPair2 result2: results55){
             if (result1._1().equals(result2._1())) {
                 num[a] = num[a] + 1;
                 a = a - 99999;
                 break;
             }
             a = a+1;
         }
         if (a>=0) {
             array[results55.size()][num[results55.size()]] = result1._2();
             num[results55.size()]=1;
             results55.add(result1);
         }
         else {
             array[a+99999][num[a+99999]] = result1._2();
         }
     }

     int a=0;
     for (mapToPair2 results: results55)
     {
         int k = 0;
         for (int i=0;i<num[a];i++) {
             k= reduceByKey1.apply(array[a][i],k);
         }
         ans.add(new mapToPair2(results._1(),k+2147483600));
         a=a+1;
     }
     return ans;
}
}