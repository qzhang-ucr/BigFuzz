package edu.ucla.cs.bigfuzz.customarray.inapplicable.SymbolicStateOutofBounds;

public class map2 {
   public static void main(String[] args) { 
       apply(1);
   }
  static final map2 apply(int l){
  int dis=1;
  int tmp=l;
  if (l <= 0)   dis=0;
 else   while (tmp != 1) {
    if (tmp % 2 == 0)     tmp/=2;
 else     tmp=3 * tmp + 1;
    dis++;
  }
  return new map2(l,dis);
}
String sa,sb;

int ia,ib;
public int _1(){
	return ia;
}
public int _2(){
	return ib;
}
public map2(int k, int v){
        ia = k;
        ib = v;
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