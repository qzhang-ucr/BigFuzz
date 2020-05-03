package edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades;

public class map4 {
   public static void main(String[] args) { 
       apply("CS:123");
   }
  static final map4 apply(String l){
  String a[]=l.split(":");
  return new map4(a[0],(Integer.parseInt(a[1])));
}
String sa,sb;

int ia,ib;
public String _1(){
	return sa;
}
public int _2(){
	return ib;
}
public map4(int k, int v){
        ia = k;
        ib = v;
}
public map4(String k, int v){
        sa = k;
        ib = v;
}
public map4(int k, String v){
        ia = k;
        sb = v;
}
public map4(String k, String v){
        sa = k;
        sb = v;
}}