package edu.ucla.cs.bigfuzz.customarray;

import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import edu.ucla.cs.bigfuzz.dataflow.*;
import edu.ucla.cs.bigfuzz.sparkprogram.WordCount;
import janala.logger.inst.METHOD_BEGIN;
import janala.logger.inst.MemberRef;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CustomArray {

    private ArrayList<String> list;

    public void CustomArray() {

    }

    public static String readStr(String inputPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        //List<String> lists = new ArrayList<String>();
        String list =null;
        String readLine = null;
        while((readLine = br.readLine()) != null){
            list = list+readLine;
            /*String[] wordsArr1 = readLine.split("[^a-zA-Z]");
            for (String word : wordsArr1) {
                if(word.length() != 0){
                    lists.add(word);
                }
            }*/
        }

        //br.close();
        return list;
    }


    public static ArrayList<String> read(String inputPath) throws IOException {
        ArrayList<String> list  = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        //List<String> lists = new ArrayList<String>();
        String readLine = null;
        while((readLine = br.readLine()) != null){
            list.add(readLine);
            /*String[] wordsArr1 = readLine.split("[^a-zA-Z]");
            for (String word : wordsArr1) {
                if(word.length() != 0){
                    lists.add(word);
                }
            }*/
        }

        //br.close();
        return list;
    }

    public static ArrayList<SalaryItem> map1(ArrayList<String> lines)
    {
//        System.out.println("Generating Data Flow Event: Map");

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));


        ArrayList<SalaryItem> ret = new ArrayList<SalaryItem>();

        for(String line : lines)
        {
            //System.out.println(line);
            int salary = 0;

            int age = 0;
            String[] columns = line.split(",");
            String zipCode = columns[0];
            if(columns[2].charAt(0)=='$')
            {
                salary = Integer.parseInt(columns[2].substring(1).replace(",",""));
            }
            else
            {
                salary = Integer.parseInt(columns[2].replace(",",""));
            }
            age = Integer.parseInt(columns[1]);
            SalaryItem item = new SalaryItem(zipCode, age, salary);
            ret.add(item);
        }

        //System.out.println(ret);
        return ret;
    }
    public static ArrayList<SalaryItem> filter1(ArrayList<SalaryItem> items, String zipcode)
    {
        ArrayList<SalaryItem> ret = new ArrayList<SalaryItem>();
        int arm = 0;
        for(SalaryItem item:items)
        {
            if(item.getZipcode().equals(zipcode))
            {
                ret.add(item);
            }
        }

//        System.out.println(ret.size());
        if( !ret.isEmpty()) arm = 1;

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber, arm));

        return ret;
    }

    public static ArrayList<Pair<String, Integer>> map2(ArrayList<SalaryItem> items)
    {
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));


        ArrayList<Pair<String, Integer>> ret = new ArrayList<Pair<String, Integer>>();
        for(SalaryItem item:items)
        {
            int salary = item.getSalary();
            int age = item.getAge();
            String key = "";
            if(age>=20&&age<40)
            {
                key = "20-40";//ret.add(new Pair<String, Integer>("20-30", salary));
            }
            else if(age>=40&&age<=65)
            {
                key = "40-65";//ret.add(new Pair<String, Integer>("40-65", salary));
            }
            else if(age<20)
            {
                key = "0-19";
            }
            else
            {
                key = ">65";
            }
            ret.add(new ImmutablePair<>(key, salary));
        }
        return ret;
    }

    public static ArrayList<Pair<String, Pair<Integer, Integer>>> mapValues1(ArrayList<Pair<String, Integer>> arrayList)
    {
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));

        ArrayList<Pair<String, Pair<Integer, Integer>>> ret = new ArrayList<Pair<String, Pair<Integer, Integer>>>();
        for(Pair<String, Integer> item:arrayList)
        {
            ret.add(new ImmutablePair<>(item.getKey(), new ImmutablePair<Integer, Integer>(item.getValue(), 1)));
        }
        return ret;
    }

    public static Map<String, Pair<Integer, Integer>>  reduceByKey1(ArrayList<Pair<String, Pair<Integer, Integer>>> arrayList)
    {
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));


        Map<String, Pair<Integer, Integer>> res = new HashMap<>();
        res.put("0-19", new ImmutablePair<>(0,0));
        res.put("20-40", new ImmutablePair<Integer, Integer>(0,0));
        res.put("40-65", new ImmutablePair<Integer, Integer>(0,0));
        res.put(">65", new ImmutablePair<Integer, Integer>(0,0));
        //System.out.println("reduceByKey1");
        for(Pair<String, Pair<Integer, Integer>> item:arrayList)
        {
            Pair<Integer, Integer> p = res.get(item.getKey());
            //System.out.println(item.getKey()+", "+p.getKey()+","+p.getValue());
            int salary = p.getKey() + item.getValue().getKey();
            int count = p.getValue() + item.getValue().getValue();
            Pair<Integer, Integer> newp = new ImmutablePair<Integer, Integer>(salary, count);
            res.put(item.getKey(), newp);
            p = res.get(item.getKey());
            //System.out.println(item.getKey()+", "+p.getKey()+","+p.getValue());
        }
        return res;
    }

    public static Map<String, Double> mapValues2(Map<String, Pair<Integer, Integer>> mmp)
    {
        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));


        Map<String, Double> ret = new HashMap<>();
        Iterator iterator = mmp.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = (String) iterator.next();
            Pair<Integer, Integer> p = mmp.get(key);
            double value = 0;
            if(p.getValue()!=0)
            {
                value = p.getKey()*1.0/p.getValue();
            }
            ret.put(key, value);
        }
        return ret;
    }

    public static ArrayList<ArrayList<Integer>> map(ArrayList<String> lines)
    {
//        System.out.println("Generating Data Flow Event: Map");

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapEvent(iid, method, callersLineNumber));


        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<10;i++)
        {
            ret.add(new ArrayList<Integer>());
        }
        for(String line : lines)
        {
            System.out.println(line);
            int val = 0;
            int zipCode = 0;
            int age = 0;
            String[] columns = line.split(",");
            if(columns[2].charAt(0)=='$')
            {
                val = Integer.parseInt(columns[2].substring(1).replace(",",""));
            }
            else
            {
                val = Integer.parseInt(columns[2].replace(",",""));
            }
            age = Integer.parseInt(columns[1])/10;
            ret.get(age).add(val);
        }

        //System.out.println(ret);
        return ret;
    }

    public static ArrayList<Integer> filter(ArrayList<Integer> input, Integer maximum)
    {
        System.out.println("Generating Data Flow Event: Filter");

        ArrayList<Integer> ret = new ArrayList<Integer>();

        int arm = 0;
        for(Integer num : input)
        {
            if(num<maximum)
            {
                ret.add(num);
            }
        }
        System.out.println(ret.size());
        if( !ret.isEmpty()) arm = 1;

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber, arm));

        return ret;
    }
    /*public static Integer reduce(ArrayList<Integer> input)
    {
        System.out.println("Generating Data Flow Event: Reduce");

        //int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        //int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        //MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        //       MemberRef method = new METHOD_BEGIN("examples.A", "foo", "()V");

        // line number if it exists

        //System.out.println(callersLineNumber);
        // Generate a custom event!
        //TraceLogger.get().emit(new ReduceEvent(iid, method, callersLineNumber));


        Integer sum = 0;
        for(Integer num : input)
        {
            sum = sum + num;
        }

        return sum;
    }*/
    public static ArrayList<Integer> reduce(ArrayList<ArrayList<Integer>> input)
    {
//        System.out.println("Generating Data Flow Event: Reduce");

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new ReduceEvent(iid, method, callersLineNumber));


        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(ArrayList<Integer> gg : input)
        {
            int sum = 0;
            for(Integer num:gg)
            {
                sum = sum + num;
            }
            ret.add(sum);
        }

        return ret;
    }
    public static ArrayList<String> addLine(String inputPath) throws IOException {
        ArrayList<String> list  = new ArrayList<String>();
        list.add(inputPath);
        //br.close();
        return list;
    }

    public static ArrayList<String> flatMap(ArrayList<String> list) {
        ArrayList<String> words  = new ArrayList<String>();
        for (String line : list) {
            String[] wordsArr1 = line.split("[^a-zA-Z]");
            for (String word : wordsArr1) {
                if (word.length() != 0) {
                    words.add(word);
                }
            }
        }
        return words;
    }
    public static ArrayList<String> flatMap() {
        System.out.println("Generating Data Flow Event: FlatMap");

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new FlatMapEvent(iid, method, callersLineNumber));

        ArrayList<String> words  = new ArrayList<String>();
        return words;
    }
    public static ArrayList<Map<String, Integer>> mapToPair(List<String> words) {
        System.out.println("Generating Data Flow Event: mapToPair");

//        System.out.println(Thread.currentThread().getStackTrace().length);
//
//        for (int i = 0; i < Thread.currentThread().getStackTrace().length; i++)
//        {
//            System.out.println(Thread.currentThread().getStackTrace()[i].getMethodName() + " "+ Thread.currentThread().getStackTrace()[i].getLineNumber());
//        }

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new MapToPairEvent(iid, method, callersLineNumber));


        int maxNumMap = 5;
        //int counter = 0;
        ArrayList<Map<String, Integer>> wordsCountMapList = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> wordsCountMap = new TreeMap<String, Integer>();
        for(String word : words)
        {
            if(wordsCountMap.size()==maxNumMap)
            {
                wordsCountMapList.add(wordsCountMap);
                wordsCountMap = new TreeMap<String, Integer>();
            }
            if(wordsCountMap.get(word) != null){
                wordsCountMap.put(word, wordsCountMap.get(word) + 1);
            }else {
                wordsCountMap.put(word, 1);
            }
        }
        if(wordsCountMap.size()!=maxNumMap&&wordsCountMap.size()>0)
        {
            wordsCountMapList.add(wordsCountMap);
        }
        return wordsCountMapList;
    }
    public static Map<String, Integer> reduceByKey(ArrayList<Map<String, Integer>> wordsCountMapList) {
        System.out.println("Generating Data Flow Event: ReduceByKey");

//        for (int i = 0; i < Thread.currentThread().getStackTrace().length; i++)
//        {
//            System.out.println(Thread.currentThread().getStackTrace()[i].getMethodName() + " "+ Thread.currentThread().getStackTrace()[i].getLineNumber());
//        }

        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();

        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location
        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "()V"); // containing method

        // Generate a custom event!
        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));


        Map<String, Integer> wordsCountMap;
        if (wordsCountMapList.size()==0)
        {
            wordsCountMap = new TreeMap<String, Integer>();
            return wordsCountMap;
        }
        wordsCountMap = wordsCountMapList.get(0);
        for(int i=1;i<wordsCountMapList.size();i++)
        {
            Map<String, Integer> temp = wordsCountMapList.get(i);
            for(Map.Entry<String, Integer>  entry : temp.entrySet())
            {
                String word = entry.getKey();
                Integer value = entry.getValue();
                if(wordsCountMap.get(word) != null){
                    wordsCountMap.put(word, wordsCountMap.get(word) + value);
                }else {
                    wordsCountMap.put(word, 1);
                }
            }
        }

        return wordsCountMap;
    }
}