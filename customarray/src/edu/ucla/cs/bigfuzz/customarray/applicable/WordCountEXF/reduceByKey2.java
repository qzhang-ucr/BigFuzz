package edu.ucla.cs.bigfuzz.customarray.applicable.WordCountEXF;

public class reduceByKey2 {

 static int applyReduce( int[] a) {
   int s = a[0];
   for(int i = 1 ; i < 2 ; i++){
       s = apply$mcIII$sp( s , a[i] );
   }
   return s;
}
 static int apply$mcIII$sp(int x$1,int x$2){
  return x$1 + x$2;
}
}