package edu.ucla.cs.bigfuzz.customarray;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import scala.*;
import java.util.*;public class CommuteTypeCustomArray {

    private ArrayList<Object> list;

    public void CommuteTypeCustomArray() {

    }
 public static ArrayList< map5> Map1(ArrayList<String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< map5> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for (String results: result){ans.add( map5.apply( results));}
return ans;
}
 public static ArrayList< map3> Map2(ArrayList<String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< map3> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for ( String results: result){ans.add( map3.apply( results));}
return ans;
}
 public static ArrayList< map3> Filter1(ArrayList< map3> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< map3> ans = new ArrayList<>();
for ( map3 results: result){if (filter2.apply(results._1(),results._2()))ans.add(results );}
int arm = 0;
if (!ans.isEmpty()) arm=1;
     TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,arm));
return ans;
}
 public static ArrayList< join6> Join1(ArrayList< map3> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< join6> ans = new ArrayList<>();
        TraceLogger.get().emit(new JoinEvent(iid, method, callersLineNumber));
for ( map3 results: result){ans.add( join6.apply( results));}
return ans;
}
 public static ArrayList< map1> Map3(ArrayList< join6> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< map1> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for ( join6 results: result){ans.add( map1.apply( results));}
return ans;
}
 public static ArrayList< map1> ReduceByKey1(ArrayList< map1> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));
        ArrayList< map1> ans =new ArrayList<>();
        int[][] array;  //prepare for reduce by key, array[][] records number list
        int[] num; // num[] records how many same item for one specific item
        num = new int [result.size()+1];
        array = new int [result.size()+1][result.size()+1];
        ArrayList< map1> results55 = new ArrayList<>();
        for ( map1 result1: result){
            int a = 0; //flag
            for ( map1 result2: results55){
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
        for ( map1 results: results55)
        {
            int k = 0;
            for (int i=0;i<num[a];i++) {
                k=reduceByKey4.applyReduce(array[a]);
            }
            ans.add(new  map1(results._1(),k));
            a=a+1;
        }
        return ans;
    }}