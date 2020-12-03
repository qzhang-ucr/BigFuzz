import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class WordCountDriver {

@Fuzz
    public void testWordCount(String fileName) throws IOException {
        System.out.println("WordCountDriver::testWordCount: "+fileName);
        WordCount analysis = new WordCount();
        analysis.WordCount(fileName);
    }
}
