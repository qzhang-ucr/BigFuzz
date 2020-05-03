package edu.ucla.cs.bigfuzz.customarray.inapplicable.Property;


import scala.Tuple4;
import scala.runtime.BoxesRunTime;

public class map1 {
   public static void main(String[] args) { 
       apply(new Tuple4(1.0,1,1.0,""));
   }


    static final Tuple4 apply(Tuple4 s){
  float a= (float) s._1();
  for (int i=0; i < BoxesRunTime.unboxToInt(s._2()); ) {
    i++;
    a*=1 + (float) s._3();
  }
  return new Tuple4(BoxesRunTime.boxToFloat(a),s._2(),s._3(),s._4());
}
}