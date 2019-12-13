import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class SalaryAnalysis1Driver {

@Fuzz
    public void testSalaryAnalysis1(String fileName) throws IOException {
        System.out.println("SalaryAnalysis1Driver::testSalaryAnalysis1: "+fileName);
        SalaryAnalysis1 analysis = new SalaryAnalysis1();
        analysis.SalaryAnalysis1(fileName);
    }
}
