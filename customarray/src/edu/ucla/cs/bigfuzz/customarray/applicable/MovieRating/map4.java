package edu.ucla.cs.bigfuzz.customarray.applicable.MovieRating;

public class map4 {
   public static void main(String[] args) { 
       apply(": ,");
   }
  static final map4 apply(String line){
  String arr[]=line.split(":");
  String movie_str=arr[0];
  String ratings=arr[1].split(",")[0].split("_")[1];
  return new map4(movie_str,ratings.substring(0,1));
}
String sa,sb;

int ia,ib;
public String _1(){
	return sa;
}
public String _2(){
	return sb;
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