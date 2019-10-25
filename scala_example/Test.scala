import edu.berkeley.cs.jqf.fuzz.{Fuzz, JQF}
import org.junit.runner.RunWith

class test1 {
  def Larger(Number: Int): Unit = {
      if(Number>100){
     //      println(Number + ": larger than 100");
	val a = 1
       }
  }
}

class test2 {
  def Less(Number: Int): Unit = {
      if(Number<0){
     //      println(Number + ": number is negative");
         val a = -1
       }
  }
}

class test3 {
  def Middle(Number: Int): Unit = {
      if(Number>=0 && Number<=100){
     //      println(Number + ": 0-100");
         val a = 0
       }
  }
}

class CheckNumber {
   def CheckNum(number: Int): Unit = {
        
        /**declare a variable*/
       //var number= (-100);
       val x = new test1()
       val y = new test2()
       val z = new test3()
       x.Larger(number)
       y.Less(number)
       z.Middle(number)
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
