public class flatMap2 { 
   public static void main(String[] args) { 
       apply();
   }
  static final Object[] apply(String r){
  return ((r.split(",")).tail());
}
}