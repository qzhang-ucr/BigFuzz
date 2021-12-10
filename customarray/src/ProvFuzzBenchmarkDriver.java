import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class ProvFuzzBenchmarkDriver {

@Fuzz
    public void testProvFuzzBenchmark(String fileName) throws IOException {
        System.out.println("ProvFuzzBenchmarkDriver::testProvFuzzBenchmark: "+fileName);
        ProvFuzzBenchmark analysis = new ProvFuzzBenchmark();
        analysis.ProvFuzzBenchmark(fileName);
    }

    public static void main(String[] args) throws IOException {
        ProvFuzzBenchmark a = new ProvFuzzBenchmark();
        a.ProvFuzzBenchmark("/home/ahmad/Documents/VT/project1/BigFuzz/dataset/ProvFuzz1/data1.csv");
    }
}
