import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator.DFOperatorCustomArray;
import edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator.map2;
import scala.Tuple2;
import scala.Tuple3;

import java.io.IOException;
import java.util.ArrayList;

public class DFOperator {
//    public static void main(String[] args) throws Exception {
//        DFOperator("/Users/a123/Desktop/BigFuzz-TestPrograms/src/dataset/twoFlow.csv");
//    }
public static void DFOperator(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<Tuple3> results1 = DFOperatorCustomArray.Map1(results0);
ArrayList<Tuple3> results2 = DFOperatorCustomArray.Filter1(results1);
ArrayList<map2> results3 = DFOperatorCustomArray.Map2(results2);
ArrayList<Tuple2> results4 = DFOperatorCustomArray.MapValues1(results3);
//ArrayList<Tuple2> results5 = DFOperatorCustomArray.ReduceByKey1(results4);
//ArrayList<Tuple2> results6 = DFOperatorCustomArray.MapValues2(results5);
}}
