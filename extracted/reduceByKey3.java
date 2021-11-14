public class reduceByKey3 { 
   public static void main(String[] args) { 
       int[] arr = ;
       applyReduce(arr);
   }
 static int applyReduce( int[] a) {
   int s = a[0];
   for(int i = 1 ; i < 2 ; i++){
       s = apply( s , a[i] );
   }
   return s;
}
 static final float apply(float acc,float e){
  return acc >= e ? e : acc;
}
}