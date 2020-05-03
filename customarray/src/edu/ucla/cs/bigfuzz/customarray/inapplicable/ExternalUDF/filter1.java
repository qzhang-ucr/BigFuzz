
package edu.ucla.cs.bigfuzz.customarray.inapplicable.ExternalUDF;
import scala.Tuple3;

public class filter1 {
   public static void main(String[] args) { 
       apply (new Tuple3<>(1, 1, 1));
   }
  static final boolean apply(Tuple3 s){
  return Inside.apply((int)(s._1()),(int)(s._2()),(int)(s._3()));
}
}