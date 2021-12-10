import edu.ucla.cs.bigfuzz.customarray.ProvFuzzBenchmarkCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class ProvFuzzBenchmark {
public void ProvFuzzBenchmark(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<String[]> results1 = ProvFuzzBenchmarkCustomArray.Map1(results0);
ArrayList<String> results2 = ProvFuzzBenchmarkCustomArray.Map2(results1);
ArrayList<String> results3 = ProvFuzzBenchmarkCustomArray.Filter1(results2);
System.out.println(results3);
}}
