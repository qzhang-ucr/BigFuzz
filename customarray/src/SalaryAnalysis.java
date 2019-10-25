import edu.ucla.cs.bigfuzz.customarray.CustomArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SalaryAnalysis {

    public void SalaryAnalysis(String inputFile) throws IOException {

        File file=new File(inputFile);
        ArrayList<String> list;
        if(file.exists())
        {
            list = CustomArray.read(inputFile);
            /*for(String line : list)
            {
                System.out.println(line);
            }*/
        }
        else
        {
            System.out.println("File does not exist!");
            return;
        }
        ArrayList<ArrayList<Integer>> salary = CustomArray.map(list);
//        ArrayList<Integer> filterSalary = CustomArray.filter(salary, 100000);
        Integer sum = CustomArray.reduce(salary);
        System.out.println(sum);

    }

}