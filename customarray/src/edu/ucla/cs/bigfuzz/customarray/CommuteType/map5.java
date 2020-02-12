package edu.ucla.cs.bigfuzz.customarray.CommuteType;

public class map5 {
   public static void main(String[] args) { 
       apply(",");
   }
  static final map5 apply(String line1){
  String cols[]=line1.split(",");
  return new map5(cols[1],(Integer.parseInt(cols[3]) / Integer.parseInt(cols[4])));
}
String sa,sb;

int ia,ib;
public String _1(){
	return sa;
}
public int _2(){
	return ib;
}
public map5(int k, int v){
        ia = k;
        ib = v;
}
public map5(String k, int v){
        sa = k;
        ib = v;
}
public map5(int k, String v){
        ia = k;
        sb = v;
}
public map5(String k, String v){
        sa = k;
        sb = v;
}}