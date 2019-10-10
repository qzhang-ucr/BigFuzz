import edu.ucla.cs.bigfuzz.customarray.CustomArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WordCount {
    public void WordCount(String inputFile) throws IOException {

        File file=new File(inputFile);
        ArrayList<String> list;
        if(file.exists())
        {
            list = CustomArray.read(inputFile);
        }

        list = CustomArray.addLine(inputFile);
        //list.add(inputFile)
//      System.out.println("hi, "+ list);
        ArrayList<String> words = CustomArray.flatMap();
        ArrayList<Map<String, Integer>> tamResult = CustomArray.mapToPair(words);
        Map<String, Integer> counts = CustomArray.reduceByKey(tamResult);
//        for(Map.Entry<String, Integer>  entry : counts.entrySet())
//        {
//            String word = entry.getKey();
//            Integer value = entry.getValue();
//            System.out.println(word+", "+value);
//        }
    }
}
