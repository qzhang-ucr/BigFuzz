package edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades;

public class flatMap5 {
   public static void main(String[] args) { 
       System.out.println(apply("aaaa \n a").length);
   }
  static final String[] apply(String line){
  String arr[]=line.split(",");
  System.out.println(arr[0]);
  return (arr);
}
}