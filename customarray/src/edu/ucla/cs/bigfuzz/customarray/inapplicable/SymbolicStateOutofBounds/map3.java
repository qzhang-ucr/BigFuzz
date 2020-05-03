package edu.ucla.cs.bigfuzz.customarray.inapplicable.SymbolicStateOutofBounds;

public class map3 {
   public static void main(String[] args) { 
       apply(",1");
   }
  static final int apply(String s){
  String cols[]=s.split(",");
  System.out.println("length:" + cols.length);
  return Integer.parseInt(cols[1]);
}
}