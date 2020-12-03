import edu.ucla.cs.bigfuzz.customarray.*;
import javafx.util.Pair;
import scala.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class CommuteType {
public void CommuteType(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<String> results1 = CustomArray.read(inputFile);
ArrayList< map5>results2 = CommuteTypeCustomArray.Map1(results0);
ArrayList< map3>results3 = CommuteTypeCustomArray.Map2(results1);
ArrayList< map3>results4 = CommuteTypeCustomArray.Filter1(results3);
ArrayList< join6>results5 = CommuteTypeCustomArray.Join1(results2);
ArrayList< map1>results6 = CommuteTypeCustomArray.Map3(results5);
ArrayList< map1>results7 = CommuteTypeCustomArray.ReduceByKey1(results6);
}}
