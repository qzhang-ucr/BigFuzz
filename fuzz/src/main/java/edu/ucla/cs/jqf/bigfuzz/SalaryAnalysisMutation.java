package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SalaryAnalysisMutation implements BigFuzzMutation{

    Random r = new Random();
    int maxDuplicatedTimes = 1000;
    int maxGenerateTimes = 1000;
    int maxGenerateValue = 10000000;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
    ArrayList<String> fileRows = new ArrayList<String>();

    /**
     * Randomly duplicate some lines and then randomly insert into the input lines
     * @param rows
     */
    public void randomDuplicateRows(ArrayList<String> rows)
    {
        int ind = r.nextInt(rows.size());
        int duplicatedTimes = r.nextInt(maxDuplicatedTimes)+1;
        String duplicatedValue = rows.get(ind);
        for(int i=0;i<duplicatedTimes;i++)
        {
            int insertPos = r.nextInt(rows.size());
            rows.add(insertPos, duplicatedValue);
        }
    }

    /**
     * Randomly generate some lines and then randomly insert into the input lines
     * @param rows
     */
    public void randomGenerateRows(ArrayList<String> rows)
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
            int insertPos = r.nextInt(rows.size());

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
            rows.add(insertPos, numberAsString);
        }
    }

    public void randomGenerateOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        for(int i=0;i<generatedTimes;i++)
        {
            int rowID = r.nextInt(rows.size());
            String row = rows.get(rowID);
            String[] columns = row.split(",");
            columns[columnID] = Integer.toString(r.nextInt(maxV-minV)+minV);
            int insertPos = r.nextInt(rows.size());

            String insertRow = columns[0];

            for(int j=1;j<columns.length;j++)
            {
                insertRow = insertRow + ","+columns[j];
            }

            rows.add(insertPos, insertRow);
        }
    }

    public void randomDuplacteOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        ArrayList<String> tempRows = new ArrayList<String>(rows);
        for(int i=0;i<rows.size();i++) {
            String row = rows.get(i);
            String[] columns = row.split(",");
            int val = Integer.parseInt(columns[columnID]);
            if (val >= minV && val <= maxV) {
                int insertPos = r.nextInt(tempRows.size());
                tempRows.add(insertPos, row);
            }
        }
        rows = tempRows;
    }

    public void improveOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
        for(int i=0;i<rows.size();i++) {
            String row = rows.get(i);
            String[] columns = row.split(",");
            int val = Integer.parseInt(columns[columnID]);
            if (val < minV || val > maxV) {
                columns[columnID] = Integer.toString(r.nextInt(maxV-minV)+minV);
                String insertRow = columns[0];

                for(int j=1;j<columns.length;j++)
                {
                    insertRow = insertRow + ","+columns[j];
                }

                rows.set(i, insertRow);
            }
        }
    }

    public void writeFile(String outputFile) throws IOException {
//        String path = "/home/qzhang/Programs/BigFuzz/dataset/" + outputFile;
        File fout = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < fileRows.size(); i++) {
            bw.write(fileRows.get(i));
            bw.newLine();
        }

        bw.close();
    }
    public void mutate(String inputFile) throws IOException
    {

        File file=new File(inputFile);

        ArrayList<String> rows = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        if(file.exists())
        {
            String readLine = null;
            while((readLine = br.readLine()) != null){
                rows.add(readLine);
            }
        }
        else
        {
            System.out.println("File does not exist!");
            return;
        }

        mutate(rows);

        fileRows = rows;
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

    public void mutate(ArrayList<String> list)
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
        int columnID = r.nextInt(3);
        if(method == 0){
            if(columns[columnID].charAt(0)=='$')
            {
                columns[columnID] = "$"+Integer.toString(r.nextInt());
            }
            else
            {
                columns[columnID] = Integer.toString(r.nextInt());
            }
        }
        else if(method==1) {
            int value = 0;
            if(columns[columnID].charAt(0)=='$')
            {
                value = Integer.parseInt(columns[columnID].substring(1));
            }
            else
            {
                value = Integer.parseInt(columns[columnID]);
            }
            float v = (float)value + r.nextFloat();
            columns[columnID] = Float.toString(v);
        }
        else if(method==2) {
            char temp = (char)r.nextInt(256);
            int pos = r.nextInt(columns[columnID].length());
            columns[columnID] = columns[columnID].substring(0, pos)+temp+columns[columnID].substring(pos);
        }
        else if(method==3) {
            columns = removeOneElement(columns, columnID);
        }
        else if(method==4) {
            String one = Integer.toString(r.nextInt(10000));
            columns = AddOneElement(columns, one, columnID);
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
