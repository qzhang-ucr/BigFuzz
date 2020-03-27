
import edu.ucla.cs.bigfuzz.customarray.applicable.WordCountEXF.WordCountNewCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.WordCountEXF.map3;

import java.io.IOException;
import java.util.ArrayList;

public class WordCountNew {
public void WordCountNew(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<String[]> results1 = WordCountNewCustomArray.FlatMap1(results0);
ArrayList<map3> results2 = WordCountNewCustomArray.Map1(results1);
ArrayList<map3> results3 = WordCountNewCustomArray.ReduceByKey1(results2);
ArrayList<map3> results4 = WordCountNewCustomArray.Filter1(results3);
}}
