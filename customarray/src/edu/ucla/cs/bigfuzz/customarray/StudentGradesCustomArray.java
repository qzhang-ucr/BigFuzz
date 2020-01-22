package edu.ucla.cs.bigfuzz.customarray;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.dataflow.*;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;public class StudentGradesCustomArray {

    private ArrayList<Object> list;

    public void StudentGradesCustomArray() {

    }
 public static ArrayList<Object> FlatMap1(ArrayList<String> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> FlatMap2(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> Map1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> Map2(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> ReduceByKey1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> Filter1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,0));ArrayList<Object> k = new ArrayList<>();
return k;

}
}