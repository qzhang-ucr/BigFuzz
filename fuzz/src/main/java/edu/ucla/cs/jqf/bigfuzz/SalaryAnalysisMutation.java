package edu.ucla.cs.jqf.bigfuzz;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
            String numberAsString = decimalFormat.format(number);
            if(r.nextBoolean())
            {
                numberAsString = "$"+numberAsString;
            }
            int insertPos = r.nextInt(list.size());
            list.add(insertPos, numberAsString);
        }
    }

    public void writeFile(String outputFile) throws IOException {
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
        File file=new File(inputFile);
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
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
        randomGenerate(list);
        randomDuplicate(list);
        /*System.out.println("After Mutation: "+list.size());
        for(String line : list)
        {
            System.out.println(line);
        }*/

        fileLines = list;
    }

    /*public static void main(String[] args) throws IOException{

        SalaryAnalysisMutation mutation = new SalaryAnalysisMutation();
        mutation.mutate("/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary.csv");
        mutation.writeFile("/home/qzhang/Downloads/BigTest-JPF-integrated/benchmarks/src/datasets/salary2.csv");
    }*/

}
