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
 public static ArrayList<Object> flatMap1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> flatMap2(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> map1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> map2(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> reduceByKey1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
 public static ArrayList<Object> filter1(ArrayList<Object> lines)
{
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));ArrayList<Object> k = new ArrayList<>();
return k;

}
}