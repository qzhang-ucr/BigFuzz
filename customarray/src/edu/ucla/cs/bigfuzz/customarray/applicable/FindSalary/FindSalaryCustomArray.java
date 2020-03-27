package edu.ucla.cs.bigfuzz.customarray.applicable.FindSalary;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;

import java.util.*;public class FindSalaryCustomArray {

    private ArrayList<Object> list;

    public void FindSalaryCustomArray() {

    }
 public static ArrayList< String> Map1(ArrayList<String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList< String> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for (String results: result){ans.add( map4.apply( results));}
return ans;
}
 public static ArrayList<Integer> Map2(ArrayList< String> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList<Integer> ans = new ArrayList<>();
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));
for ( String results: result){ans.add( map3.apply( results));}
return ans;
}
 public static ArrayList<Integer> Filter1(ArrayList<Integer> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList<Integer> ans = new ArrayList<>();
        int arm = 1;
for ( int results: result){if (filter2.apply$mcZI$sp(results))ans.add(results );}
if (ans.isEmpty()) {arm=0;}
     TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,arm));
return ans;
}
 public static ArrayList<Integer> Reduce1(ArrayList<Integer> result){
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        ArrayList<Integer> ans = new ArrayList<>();
        TraceLogger.get().emit(new ReduceEvent(iid, method, callersLineNumber));
        int[] ans1 = new int[2];
for ( int results: result){ans1[1]=results;ans1[0]=reduce1.applyReduce(ans1);}
ans.add(ans1[0]);
return ans;
}
}