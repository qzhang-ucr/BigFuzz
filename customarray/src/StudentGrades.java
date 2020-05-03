import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades.StudentGradesCustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades.map3;
import edu.ucla.cs.bigfuzz.customarray.applicable.StudentGrades.map4;

import java.io.IOException;
import java.util.ArrayList;

public class StudentGrades {


public void StudentGrades(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<String[]> results1 = StudentGradesCustomArray.FlatMap1(results0);
ArrayList<map4> results2 = StudentGradesCustomArray.Map1(results1);
ArrayList<map3> results3 = StudentGradesCustomArray.Map2(results2);
ArrayList<map3> results4 = StudentGradesCustomArray.ReduceByKey1(results3);
//System.out.println(results4.get(1)._1());
ArrayList<map3> results5 = StudentGradesCustomArray.Filter1(results4);
//System.out.println(results5.size());
}}
