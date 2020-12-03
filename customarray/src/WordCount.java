import edu.ucla.cs.bigfuzz.customarray.applicable.WordCount.WordCountCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;

import java.io.IOException;
import java.util.ArrayList;

public class WordCount {
public void WordCount(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<Object> results1 = WordCountCustomArray.FlatMap1(results0);
ArrayList<Object> results2 = WordCountCustomArray.MapToPair1(results1);
ArrayList<Object> results3 = WordCountCustomArray.ReduceByKey1(results2);
}}
