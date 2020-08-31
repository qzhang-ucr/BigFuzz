import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class CombinedDriver {

@Fuzz
    public void testCombined(String fileName) throws IOException {
        System.out.println("CombinedDriver::testCombined: "+fileName);
        Combined analysis = new Combined();
        analysis.Combined(fileName);
    }
}
