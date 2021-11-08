import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class MatrixMinMaxNNDriver {

@Fuzz
    public void testMatrixMinMaxNN(String fileName) throws IOException {
        System.out.println("MatrixMinMaxNNDriver::testMatrixMinMaxNN: "+fileName);
        MatrixMinMaxNN analysis = new MatrixMinMaxNN();
        analysis.MatrixMinMaxNN(fileName);
    }
}
