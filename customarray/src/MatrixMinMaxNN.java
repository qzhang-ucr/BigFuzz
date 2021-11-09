import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.MatrixMinMaxNN.MatrixMinMaxNNCustomArray;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class MatrixMinMaxNN {
    public void MatrixMinMaxNN(String inputFile) throws IOException {
    ArrayList<String> results0 = CustomArray.read(inputFile);
    ArrayList<String[]> results1 = MatrixMinMaxNNCustomArray.FlatMap1(results0);
    ArrayList<Float> results2 = MatrixMinMaxNNCustomArray.Map1(results1);
    float results3 = MatrixMinMaxNNCustomArray.ReduceByKey1(results2);
    float results4 = MatrixMinMaxNNCustomArray.ReduceByKey2(results2);
        System.out.println(results3);
        System.out.println(results4);
    }
}
