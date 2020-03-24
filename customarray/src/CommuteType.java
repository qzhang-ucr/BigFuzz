import edu.ucla.cs.bigfuzz.customarray.CommuteType.*;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;

import java.io.IOException;
import java.util.ArrayList;

public class CommuteType {
public void CommuteType(String inputFile1, String inputFile2) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile1);
ArrayList<map5> results1 = CommuteTypeCustomArray.Map1(results0);
ArrayList<String> results2 = CustomArray.read(inputFile2);
ArrayList<map3> results3 = CommuteTypeCustomArray.Map2(results2);
ArrayList<map3> results4 = CommuteTypeCustomArray.Filter1(results3);
ArrayList<join6> results5 = CommuteTypeCustomArray.Join1(results4,results1);
ArrayList<map1> results6 = CommuteTypeCustomArray.Map3(results5);
ArrayList<map1> results7 = CommuteTypeCustomArray.ReduceByKey1(results6);
    for(int i = 0; i < results7.size(); i++)
    {
        System.out.println(results7.get(i)._1()+ " "+ results7.get(i)._2());
    }
}}
