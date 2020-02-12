import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class CommuteTypeDriver {

//@Fuzz
//    public void testCommuteType(String fileName) throws IOException {
//        System.out.println("CommuteTypeDriver::testCommuteType: "+fileName);
//        CommuteType analysis = new CommuteType();
//        analysis.CommuteType(fileName);
//    }

    public static void main(String[] args) throws IOException
    {
        CommuteType analysis = new CommuteType();
        analysis.CommuteType("/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/trips.csv", "/home/qzhang/Programs/BigFuzz-TestPrograms/src/dataset/zipcode.csv");

   }
}
