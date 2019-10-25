import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(JQF.class)
public class SalaryAnalysisDriver {

    @Fuzz
    public void testSalaryAnalysis(String fileName) throws IOException {
//        System.out.println(fileName);
//        byte[] bs = fileName.getBytes();
//        System.out.println(Arrays.toString(bs));
//        byte[] bytes = fileName.getBytes();
        System.out.println("SalaryAnalysisDriver::testSalaryAnalysis: "+fileName);
        SalaryAnalysis analysis = new SalaryAnalysis();
        //fileName = "/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary.csv";
        analysis.SalaryAnalysis(fileName);
    }

//    public static void main(String[] args) throws IOException
//    {
//
//        SalaryAnalysis salaryAnalysis = new SalaryAnalysis();
//        salaryAnalysis.SalaryAnalysis("/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary.csv");
//    }
}