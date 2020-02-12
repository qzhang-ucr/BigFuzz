package edu.ucla.cs.bigfuzz.customarray.CommuteType;

public class map3 {
   public static void main(String[] args) { 
       apply(",");
   }
  static final map3 apply(String line2){
  String cols[]=line2.split(",");
  return new map3(cols[0],cols[1]);
}
String sa,sb;

int ia,ib;

    public String _1() {
        return sa;
    }

    public String _2() {
        return sb;
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