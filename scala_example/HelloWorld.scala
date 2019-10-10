import edu.berkeley.cs.jqf.fuzz.{Fuzz, JQF}
import org.junit.runner.RunWith

@RunWith(classOf[JQF])
class HelloWorld{
  
//  def main(args: Array[String]) {
//    testOutput("HelloWorld")
//  }

  @Fuzz
  def testOutput(a: String) : Unit = {
    println("String Length is : " + a.length());
  }
}
