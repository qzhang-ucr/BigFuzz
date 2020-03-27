package edu.ucla.cs.bigfuzz.customarray.applicable.FindSalary;

public class map4 {
   public static void main(String[] args) { 
       apply("123");
   }
  static final String apply(String line){
  String i;
  return line.substring(0,1).equals("$") ? (i=line.substring(1,6)) : line;
}
}