
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@RunWith(JQF.class)

public class FindSalaryDriver {

@Fuzz
    public void testFindSalary(String fileName) throws IOException {
        System.out.println("edu.ucla.cs.bigfuzz.customarray.applicable.FindSalary.FindSalaryDriver::testFindSalary: "+fileName);

        List<String> fileList = Files.readAllLines(Paths.get(fileName));
        FindSalary analysis = new FindSalary();
        analysis.FindSalary(fileList.get(0));
    }
}
