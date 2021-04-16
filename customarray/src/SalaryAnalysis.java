import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.SalaryItem;
import org.apache.commons.lang3.tuple.Pair;

//import org.supercsv.cellprocessor.ParseDouble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
        ArrayList<SalaryItem> results1 = CustomArray.map1(list);
        ArrayList<SalaryItem> results2 = CustomArray.filter1(results1, "90024");
        ArrayList<Pair<String, Integer>> results3 = CustomArray.map2(results2);
        /*System.out.println("---------results3-----------");
        for(Pair<String, Integer> p:results3)
        {
            System.out.println(p.getKey()+": "+p.getValue());
        }*/
        ArrayList<Pair<String, Pair<Integer, Integer>>> results4 = CustomArray.mapValues1(results3);
        /*System.out.println("---------results4-----------");
        for(Pair<String, Pair<Integer, Integer>> p:results4)
        {
            System.out.println(p.getKey()+": "+p.getValue().getKey()+", "+p.getValue().getValue());
        }*/
        Map<String, Pair<Integer, Integer>> results5 = CustomArray.reduceByKey1(results4);
        /*Iterator iterator1 = results5.keySet().iterator();
        System.out.println("---------results5-----------");
        while (iterator1.hasNext())
        {
            String key = (String) iterator1.next();
            Pair<Integer, Integer> value = results5.get(key);
            //Double value = results5.get(key);
            System.out.println(key+": "+value.getKey()+", "+value.getValue());
        }*/
//        for(Map<String, Pair<Integer, Integer>> p:results5)
//        {
//            System.out.println(p.getKey()+": "+p.getValue().getKey()+", "+p.getValue().getValue());
//        }
        Map<String, Double> result6 = CustomArray.mapValues2(results5);
//        ArrayList<Integer> filterSalary = CustomArray.filter(salary, 100000);
        //ArrayList<Integer> sum = CustomArray.reduce(salary);
        /*System.out.println("--------------------");
        Iterator iterator2 = result6.keySet().iterator();
        while (iterator2.hasNext())
        {
            String key = (String) iterator2.next();
            Double value = result6.get(key);
            System.out.println(key+": "+value);
        }*/
//        System.out.println(sum);

    }

}