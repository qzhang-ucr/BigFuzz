package edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.dataflow.FilterEvent;
import edu.ucla.cs.bigfuzz.dataflow.MapEvent;
import edu.ucla.cs.bigfuzz.dataflow.MapValuesEvent;
import edu.ucla.cs.bigfuzz.dataflow.ReduceByKeyEvent;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;
import scala.Tuple2;
import scala.Tuple3;

import java.util.ArrayList;

public class DFOperatorCustomArray {

    private ArrayList<Object> list;

    public void DFOperatorCustomArray() {

    }
 public static ArrayList<Tuple3> Map1(ArrayList<String> result){
        System.out.println("map1***********");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< Tuple3> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));

         for (String results : result) {
             ans.add(map3.apply(results));
         }

System.out.println("ans size: "+ans.size());
return ans;
}
 public static ArrayList< Tuple3> Filter1(ArrayList< Tuple3> result){
        System.out.println("filter1**********");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< Tuple3> ans = new ArrayList<>();

for ( Tuple3 results: result){if (filter1.apply(results)) ans.add(results );}
     int arm = 0;
     if (!ans.isEmpty()) arm=1;
     TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,arm));
return ans;
}
 public static ArrayList<map2> Map2(ArrayList< Tuple3> result){
        System.out.println("map2*******8");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList<map2> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for ( Tuple3 results: result){ans.add( map2.apply( results));}
return ans;
}
 public static ArrayList<Tuple2> MapValues1(ArrayList<map2> result){
        System.out.println("map2****");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< Tuple2> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));
for ( map2 results: result){
    ans.add( new Tuple2(results.sa,mapValues101.apply( results._2())));}
return ans;
}
 public static ArrayList< Tuple2> ReduceByKey1(ArrayList<Tuple2> result) throws Exception {
        System.out.println("ReduceByke***********");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));

     ArrayList<Tuple2> ans =new ArrayList<>();
     Tuple2<Integer,Integer>[][] array;  //prepare for reduce by key, array[][] records number list
     int[] num; // num[] records how many same item for one specific item
     num = new int[result.size() + 1];
     array = new Tuple2 [result.size()+1][result.size()+1];
     ArrayList<Tuple2<String,Tuple2<Integer,Integer>>> results55 = new ArrayList<>();
     for (Tuple2<String,Tuple2<Integer,Integer>> result1: result){
         int a = 0; //flag
         for (Tuple2<String,Tuple2<Integer,Integer>> result2: results55){
             if (result1._1().equals(result2._1())) {
                 num[a] = num[a] + 1;
                 a = a - 99999;
                 break;
             }
             a = a+1;
         }
         if (a>=0) {
             array[results55.size()][num[results55.size()]] = result1._2();
             results55.add(result1);
         }
         else {
             array[a+99999][num[a+99999]] = result1._2();
         }
     }

     int a=0;
     for (Tuple2 results: results55)
     {
         Tuple2<Integer,Integer> k = new Tuple2<>(0,0);
         for (int i=0;i<=num[a];i++) {
             System.out.println(array[a][i]._1+";"+array[a][i]._2);
             k= reduceByKey4.apply(array[a][i]._1,array[a][i]._2,k._1,k._2);
         }
         System.out.println(k);
         ans.add(new Tuple2(results._1(),k));
         a=a+1;
     }
     return ans;
}
 public static ArrayList< Tuple2> MapValues2(ArrayList<Tuple2> result){
        System.out.println("mapvalues***********8");
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< Tuple2> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));
for ( Tuple2<String,Tuple2<Integer,Integer>> results: result){ans.add(new Tuple2(results._1() , mapValues102.apply( results._2())));}
return ans;
}
}