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
ArrayList<Object> results1 = StudentGradesCustomArray.flatMap1(result0);
ArrayList<Object> results2 = StudentGradesCustomArray.flatMap2(results1);
ArrayList<Object> results3 = StudentGradesCustomArray.map1(results2);
ArrayList<Object> results4 = StudentGradesCustomArray.map2(results3);
ArrayList<Object> results5 = StudentGradesCustomArray.reduceByKey1(results4);
ArrayList<Object> results6 = StudentGradesCustomArray.filter1(results5);
}}
