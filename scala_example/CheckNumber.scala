import edu.berkeley.cs.jqf.fuzz.{Fuzz, JQF}
import org.junit.runner.RunWith


class CheckNumber {
   def CheckNum(number: Int): Unit = {
        
        /**declare a variable*/
       //var number= (-100);
       Larger(number);
       Less(number);
       Middle(number);
  }
  def Larger(Number: Int): Unit = {
      if(Number>100){
           println(Number + ": larger than 100");
       }
  }

  def Less(Number: Int): Unit = {
      if(Number<0){
           println(Number + ": number is negative");
       }
  }
  def Middle(Number: Int): Unit = {
      if(Number>=0 && Number<=100){
           println(Number + ": 0-100");
       }
  }
}

@RunWith(classOf[JQF])
class Test {
    @Fuzz
    def testCheckNumber(a: Int) {
        val checkNum = new CheckNumber()
        checkNum.CheckNum(a)
    }
}
