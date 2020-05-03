package edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator;

import scala.Tuple2;

public class reduceByKey4 {


 static final Tuple2 apply(int x_t1, int x_t2, int y_t1, int y_t2){
  return new Tuple2(x_t1 + y_t1,x_t2 + y_t2);
}
String sa,sb;

int ia,ib;
public int _1(){
	return ia;
}
public int _2(){
	return ib;
}
public reduceByKey4(int k, int v){
        ia = k;
        ib = v;
}
public reduceByKey4(String k, int v){
        sa = k;
        ib = v;
}
public reduceByKey4(int k, String v){
        ia = k;
        sb = v;
}
public reduceByKey4(String k, String v){
        sa = k;
        sb = v;
}}