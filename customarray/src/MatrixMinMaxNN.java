import edu.ucla.cs.bigfuzz.customarray.MatrixMinMaxNNCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class MatrixMinMaxNN {
public void MatrixMinMaxNN(String inputFile) throws IOException {
String results0 = CustomArray.readStr(inputFile);
ArrayList<Object> results1 = MatrixMinMaxNNCustomArray.FlatMap1(results0);
ArrayList<Object> results2 = MatrixMinMaxNNCustomArray.Map1(results1);
ArrayList<Object> results3 = MatrixMinMaxNNCustomArray.ReduceByKey1(results2);
ArrayList<Object> results4 = MatrixMinMaxNNCustomArray.ReduceByKey2(results2);
}}
