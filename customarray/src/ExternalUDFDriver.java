

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JQF.class)
public class ExternalUDFDriver {

@Fuzz
    public void testExternalUDF(String fileName) throws IOException {
        System.out.println("edu.ucla.cs.bigfuzz.customarray.inapplicable.ExternalUDF.ExternalUDFDriver::testExternalUDF: "+fileName);
        ExternalUDF analysis = new ExternalUDF();

        List<String> fileList = Files.readAllLines(Paths.get(fileName));
        System.out.println("file list: *****" + fileList.size());
        analysis.ExternalUDF(fileList.get(0));
    }

    public static void main(String[] args) throws IOException {

        ExternalUDF analysis = new ExternalUDF();
        analysis.ExternalUDF("/home/qzhang/Programs/BigFuzz/dataset/salary1.csv");
    }
}
