//package edu.ucla.cs.bigfuzz.customarray.CommuteType;
//
//import java.lang.Math;
//
//
//public class map1 {
//   public static void main(String[] args) {
//       apply("1" , 2, "1");
//   }
//  static final map1 apply(String s_t1, int s_t2, String s_t3){
//  return s_t2 <= 40 ? s_t2 <= 15 ? new map1("walk",(1)) : new map1("public",(1)) : new map1("car",(1));
//}
//String sa,sb;
//
//int ia,ib;
//public int _1(){
//	return ia;
//}
//public int _2(){
//	return ib;
//}
//public map1(int k, int v){
//        ia = k;
//        ib = v;
//}
//public map1(String k, int v){
//        sa = k;
//        ib = v;
//}
//public map1(int k, String v){
//        ia = k;
//        sb = v;
//}
//public map1(String k, String v){
//        sa = k;
//        sb = v;
//}}

package edu.ucla.cs.bigfuzz.customarray.CommuteType;

import java.lang.Math;


public class map1 {
    public static void main(String[] args) {
        apply("1" , 2, "1");
    }
    static final map1 apply(String s_t1, int s_t2, String s_t3){
        return Math.log10(s_t2) <= 40 ? Math.log10(s_t2) <= 15 ? new map1("walk",(1)) : new map1("public",(1)) : new map1("car",(1));
    }
    String sa,sb;

    int ia,ib;
    public String _1(){
        return sa;
    }
    public int _2(){
        return ib;
    }
    public map1(int k, int v){
        ia = k;
        ib = v;
    }
    public map1(String k, int v){
        sa = k;
        ib = v;
    }
    public map1(int k, String v){
        ia = k;
        sb = v;
    }
    public map1(String k, String v){
        sa = k;
        sb = v;
    }}