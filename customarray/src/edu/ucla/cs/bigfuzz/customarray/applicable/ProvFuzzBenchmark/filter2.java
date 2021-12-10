package edu.ucla.cs.bigfuzz.customarray.applicable.ProvFuzzBenchmark;

public class filter2 {
   public static void main(String[] args) { 
       apply("female");
   }
  public static final boolean apply(String x){
  return !x.equals("");
}
}