package edu.ucla.cs.bigfuzz.customarray.applicable.ProvFuzzBenchmark;

//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.rdd.RDD;
//import scala.*;
//import scala.collection.mutable.ArrayOps;
//import scala.reflect.ClassTag$;
//import scala.runtime.*;

public class map3 {
    public static void main(String[] args) {
        apply(new String[]{"1", "female", "ComputerScience", "Senior"});
    }

    public static final String apply(String row[]) {

//        System.out.println("THIS IS WOQRKING");
//        Tuple3 tuple3_1 = new Tuple3(row[1], row[2], row[3]);
//        if (tuple3_1 != null) {
            String major = row[1]; //(String) tuple3_1._1();
            String gender = row[2]; //(String) tuple3_1._2();
            String year = row[3]; //(String) tuple3_1._3();
            if (gender.equals("female")) {
                if (major.equals("ComputerScience")) {
                    if (year.equals("Senior")) {
                        return "female CS Senior";
//        throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                    } else {
//                        throw new RuntimeException();
                        return "female CS !Senior";
//        throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                    }
                } else {
                    return "female !CS";
//      throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                }
            } else {
                if (major.equals("Economics")) {
                    if (year.equals("Senior")) {
                        return "male Econ Senior";
//        throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                    } else {
                        return "male Econ !Senior";

//        throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                    }
                } else {
                    return "male !Econ";
//      throw new scala.runtime.NonLocalReturnControl.mcV.sp(nonLocalReturnKey1$1,BoxedUnit.UNIT);
                }
            }
//        } else {
////            throw new MatchError(tuple3_1);
//            return "";
//        }
//        return "";
    }
}