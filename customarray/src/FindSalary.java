
import edu.ucla.cs.bigfuzz.customarray.applicable.FindSalary.FindSalaryCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;

import java.io.IOException;
import java.util.ArrayList;

public class FindSalary {
public void FindSalary(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<String> results1 = FindSalaryCustomArray.Map1(results0);
ArrayList<Integer> results2 = FindSalaryCustomArray.Map2(results1);
ArrayList<Integer> results3 = FindSalaryCustomArray.Filter1(results2);
ArrayList<Integer> results4 = FindSalaryCustomArray.Reduce1(results3);
}}
