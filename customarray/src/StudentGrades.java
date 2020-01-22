import edu.ucla.cs.bigfuzz.customarray.StudentGradesCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class StudentGrades {
public void StudentGrades(String inputFile) throws IOException {
ArrayList<String> result0 = CustomArray.read(inputFile);
ArrayList<Object> results1 = StudentGradesCustomArray.FlatMap1(result0);
ArrayList<Object> results2 = StudentGradesCustomArray.FlatMap2(results1);
ArrayList<Object> results3 = StudentGradesCustomArray.Map1(results2);
ArrayList<Object> results4 = StudentGradesCustomArray.Map2(results3);
ArrayList<Object> results5 = StudentGradesCustomArray.ReduceByKey1(results4);
ArrayList<Object> results6 = StudentGradesCustomArray.Filter1(results5);
}}
