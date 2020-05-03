package edu.ucla.cs.bigfuzz.customarray.inapplicable.ExternalUDF;

public class map2 {
   public static void main(String[] args) { 
       apply(",");
   }
  static final map2 apply(String s){
  String a[]=s.split(",");
  return new map2(Integer.parseInt(a[0]),(Integer.parseInt(a[1])),Integer.parseInt(a[2]));
}
String sa,sb;

int ia,ib,ic;
public int _1(){
	return ia;
}
public int _2(){
	return ib;
}
public int _3() {return  ic;}
public map2(int k, int v, int z){
        ia = k;
        ib = v;
        ic = z;
}
public map2(String k, int v){
        sa = k;
        ib = v;
}
public map2(int k, String v){
        ia = k;
        sb = v;
}
public map2(String k, String v){
        sa = k;
        sb = v;
}}