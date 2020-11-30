import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JQF.class)
public class DFOperatorDriver {

    @Fuzz
    public void testDFOperator(String fileName) throws IOException {
        System.out.println("DFOperatorDriver::testDFOperator: "+fileName);
        DFOperator analysis = new DFOperator();
    List<String> fileList = Files.readAllLines(Paths.get(fileName));
    System.out.println("file list: *****" + fileList.size());
    analysis.DFOperator(fileList.get(0));
    }
}
