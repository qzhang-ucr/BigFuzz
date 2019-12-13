import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class SalaryAnalysis1 {
public void SalaryAnalysis1(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
<youItem> result1 = CustomArray.map1(result0);
<youItem> result2 = CustomArray.filter1(results1);
<youItem> result3 = CustomArray.map2(result2);
<youItem> result4 = CustomArray.mapValues1(result3);
<youItem> result5 = CustomArray.reduceByKey1(results4);
<youItem> result6 = CustomArray.mapValues2(results5);
}}
