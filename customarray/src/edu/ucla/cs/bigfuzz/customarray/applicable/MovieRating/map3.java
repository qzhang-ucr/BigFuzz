package edu.ucla.cs.bigfuzz.customarray.applicable.MovieRating;

public class map3 {
   public static void main(String[] args) { 
       apply(new map4("","1"));
   }
  static final map3 apply(map4 a){
  String str=a._1();
  return new map3(a._1(),(Integer.parseInt(a._2())));
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