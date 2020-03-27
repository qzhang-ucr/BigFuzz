
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
public class WordCountNewDriver {

@Fuzz
    public void testWordCountNew(String fileName) throws IOException {
        System.out.println("edu.ucla.cs.bigfuzz.customarray.applicable.WordCountEXF.WordCountNewDriver::testWordCountNew: "+fileName);
        List<String> fileList = Files.readAllLines(Paths.get(fileName));

    try {
        Scanner sc = new Scanner(new File(fileList.get(0)));

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());

        }
    } catch (FileNotFoundException e) {

    }

        WordCountNew analysis = new WordCountNew();
        analysis.WordCountNew(fileList.get(0));
    }
}
