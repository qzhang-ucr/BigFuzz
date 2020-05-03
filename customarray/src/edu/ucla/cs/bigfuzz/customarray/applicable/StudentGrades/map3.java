package edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades;

public class map3 {
   public static void main(String[] args) { 
       apply("",1);
   }
  static final map3 apply(String a_t1, int a_t2){
  return a_t2 <= 40 ? new map3((a_t1).concat(" Fail"),(1)) : new map3((a_t1).concat(" Pass"),(1));
}
String sa,sb;

int ia,ib;
public String _1(){
	return sa;
}
public int _2(){
	return ib;
}
public map3(int k, int v){
        ia = k;
        ib = v;
}
public map3(String k, int v){
        sa = k;
        ib = v;
}
public map3(int k, String v){
        ia = k;
        sb = v;
}
public map3(String k, String v){
        sa = k;
        sb = v;
}}