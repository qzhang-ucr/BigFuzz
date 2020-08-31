import edu.ucla.cs.bigfuzz.customarray.*;
import javafx.util.Pair;
import scala.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class WordCountNew {
public void WordCountNew(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList< String[]>results1 = WordCountNewCustomArray.FlatMap1(results0);
ArrayList< map2>results2 = WordCountNewCustomArray.Map1(results1);
ArrayList< map2>results3 = WordCountNewCustomArray.ReduceByKey1(results2);
}}
