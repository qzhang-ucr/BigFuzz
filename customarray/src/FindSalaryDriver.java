
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@RunWith(JQF.class)

public class FindSalaryDriver {

@Fuzz
    public void testFindSalary(String fileName) throws IOException {
        System.out.println("edu.ucla.cs.bigfuzz.customarray.applicable.FindSalary.FindSalaryDriver::testFindSalary: "+fileName);

    try {
        Scanner sc = new Scanner(new File(fileName));
        //按行读取test.txt文件内容
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());

        }
    } catch (FileNotFoundException e) {

    }
        FindSalary analysis = new FindSalary();
        analysis.FindSalary(fileName);
    }
}
