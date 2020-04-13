package edu.ucla.cs.bigfuzz.customarray.inapplicable.SymbolicStateOutofBounds;

public class filter1 {
   public static void main(String[] args) { 
       apply(1,1);
   }
  static final boolean apply(int m_t1, int m_t2){
  return m_t2==25;
}
}