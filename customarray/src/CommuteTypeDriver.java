import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class CommuteTypeDriver {

@Fuzz
    public void testCommuteType(String fileName) throws IOException {
        System.out.println("CommuteTypeDriver::testCommuteType: "+fileName);
        CommuteType analysis = new CommuteType();
        analysis.CommuteType(fileName);
    }
}
