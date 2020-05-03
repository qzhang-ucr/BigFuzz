package edu.ucla.cs.bigfuzz.customarray.inapplicable.Property;


import scala.Predef$;
import scala.Tuple4;
import scala.collection.immutable.StringOps;
import scala.runtime.BoxesRunTime;

public class map2 {
   public static void main(String[] args) { 
       apply(",");
   }
  static final Tuple4 apply(String s){
  String a[]=s.split(",");
  return new Tuple4(BoxesRunTime.boxToFloat((new StringOps(Predef$.MODULE$.augmentString(a[2]))).toFloat()),(Integer.parseInt(a[3])),BoxesRunTime.boxToFloat((new StringOps(Predef$.MODULE$.augmentString(a[4]))).toFloat()),a[6]);
}
}