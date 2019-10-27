package edu.ucla.cs.jqf.bigfuzz;

import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SalaryAnalysisMutation {

    Random r = new Random();
    int maxDuplicatedTimes = 1000;
    int maxGenerateTimes = 1000;
    int maxGenerateValue = 10000000;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
    ArrayList<String> fileLines = new ArrayList<String>();
    private void randomDuplicate(ArrayList<String> list)
    {
        int ind = r.nextInt(list.size());
        int duplicatedTimes = r.nextInt(maxDuplicatedTimes)+1;
        String duplicatedValue = list.get(ind);
        for(int i=0;i<duplicatedTimes;i++)
        {
            int insertPos = r.nextInt(list.size());
            list.add(insertPos, duplicatedValue);
        }
    }
    private void randomGenerate(ArrayList<String> list)
    {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        for(int i=0;i<generatedTimes;i++)
        {
            double tempnumber = r.nextGaussian()*10000+10000;
            if(tempnumber<0)
            {
                tempnumber = 0;
            }
            int number = (int)tempnumber;
            String numberAsString = Integer.toString(number);
            if(r.nextBoolean())
            {
                numberAsString = "$"+numberAsString;
            }
            int insertPos = r.nextInt(list.size());

            int zip = r.nextInt(89999)+1;
            int age = (int)r.nextGaussian()*45+30;
            if(age>=100)
            {
                age=99;
            }
            if(age<0)
            {
                age = 0;
            }
            numberAsString = Integer.toString(zip)+","+Integer.toString(age)+","+numberAsString;
            list.add(insertPos, numberAsString);
        }
    }

    public void writeFile(String outputFile) throws IOException {
//        String path = "/home/qzhang/Programs/BigFuzz/dataset/" + outputFile;
        File fout = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < fileLines.size(); i++) {
            bw.write(fileLines.get(i));
            bw.newLine();
        }

        bw.close();
    }
    public void mutate(String inputFile) throws IOException
    {
        // Read a file
//        String original = "/Users/zhuhaichao/Documents/Workspace/github/BigFuzz/dataset/salary1.csv+cov";
        File file=new File(inputFile);
//        File file = new File(original);
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
//        BufferedReader br = new BufferedReader(new FileReader(original));
        if(file.exists())
        {
            String readLine = null;
            while((readLine = br.readLine()) != null){
                list.add(readLine);
            }
        }
        else
        {
            System.out.println("File does not exist!");
            return;
        }

        // Mutate
        /*System.out.println("Before Mutation"+list.size());
        for(String line : list)
        {
            System.out.println(line);
        }*/
//        randomGenerate(list);
        //randomDuplicate(list);
        randomMutate(list);
        /*System.out.println("After Mutation: "+list.size());
        for(String line : list)
        {
            System.out.println(line);
        }*/

        fileLines = list;
    }
    public static String[] removeOneElement(String[] input, int index) {
        List result = new LinkedList();

        for(int i=0;i<input.length;i++)
        {
            if(i==index)
            {
                continue;
            }
            result.add(input[i]);
        }

        return (String [])result.toArray(input);
    }
    public static String[] AddOneElement(String[] input, String value, int index) {
        List result = new LinkedList();

        for(int i=0;i<input.length;i++)
        {
            result.add(input[i]);
            if(i==index)
            {
                result.add(value);
            }
        }

        return (String [])result.toArray(input);
    }

    private void randomMutate(ArrayList<String> list)
    {
        r.setSeed(System.currentTimeMillis());
        int lineNum = r.nextInt(list.size());
        // 0: random change value
        // 1: random change into float
        // 2: random insert
        // 3: random delete one column
        // 4: random add one coumn
        String[] columns = list.get(lineNum).split(",");
        int method = r.nextInt(5);
        int columID = r.nextInt(3);
        if(method == 0){
            if(columns[columID].charAt(0)=='$')
            {
                columns[columID] = "$"+Integer.toString(r.nextInt());
            }
            else
            {
                columns[columID] = Integer.toString(r.nextInt());
            }
        }
        else if(method==1) {
            int value = 0;
            if(columns[columID].charAt(0)=='$')
            {
                value = Integer.parseInt(columns[columID].substring(1));
            }
            else
            {
                value = Integer.parseInt(columns[columID]);
            }
            float v = (float)value + r.nextFloat();
            columns[columID] = Float.toString(v);
        }
        else if(method==2) {
            char temp = (char)r.nextInt(256);
            int pos = r.nextInt(columns[columID].length());
            columns[columID] = columns[columID].substring(0, pos)+temp+columns[columID].substring(pos);
        }
        else if(method==3) {
            columns = removeOneElement(columns, columID);
        }
        else if(method==4) {
            String one = Integer.toString(r.nextInt(10000));
            columns = AddOneElement(columns, one, columID);
        }
        String line = "";
        for(int j=0;j<columns.length;j++) {
            if(j==0)
            {
                line = columns[j];
            }
            else
            {
                line = line+","+columns[j];
            }
        }
        list.set(lineNum, line);
        /*for(int i=0;i<list.size();i++)
        {
            String line = list.get(i);
            String[] components = line.split(",");
            line = "";
            for(int j=0;j<components.length;j++)
            {
                if(r.nextDouble()>0.8)
                {
                    components[j] = randomChangeByte(components[j]);
                }
                if(line.equals(""))
                {
                    line = components[j];
                }
                else
                {
                    line = line+","+components[j];
                }
            }

            list.set(i, line);
        }*/
    }

    private String randomChangeByte(String instr)
    {
        // 0: random replace one char
        // 1: random delete one char
        // 2: random add one char
        String ret = "";
        int pos = r.nextInt(instr.length());
        int method = r.nextInt(3);
        if(method == 0)
        {
            char[] temp = instr.toCharArray();
            temp[pos] = (char)r.nextInt(256);
            ret = String.valueOf(temp);
        }
        else if(method==1)
        {
            ret = instr.substring(0, pos)+instr.substring(pos+1);
        }
        else
        {
            char temp = (char)r.nextInt(256);
            ret = instr.substring(0, pos)+temp+instr.substring(pos);
        }
        return ret;
    }
    /*public static void main(String[] args) throws IOException{

        SalaryAnalysisMutation mutation = new SalaryAnalysisMutation();
        mutation.mutate("/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary.csv");
        mutation.writeFile("/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary2.csv");
    }*/

}
