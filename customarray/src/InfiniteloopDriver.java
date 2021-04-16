
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
public class InfiniteloopDriver {

    @Fuzz
    public void testInfiniteloop(String fileName) throws IOException {
        System.out.println("InfiniteloopDriver::testInfiniteloop: "+fileName);

        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());

            }
        } catch (FileNotFoundException e) {

        }

        List<String> fileList = Files.readAllLines(Paths.get(fileName));

        Infiniteloop analysis = new Infiniteloop();
        analysis.Infiniteloop(fileList.get(0));
    }

    public static void main(String[] args) throws IOException {
        String fileName = "dataset/salary1.csv";
        Infiniteloop analysis = new Infiniteloop();
        analysis.Infiniteloop(fileName);
    }
}
