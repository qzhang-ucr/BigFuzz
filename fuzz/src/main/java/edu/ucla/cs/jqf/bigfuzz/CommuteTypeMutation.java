package edu.ucla.cs.jqf.bigfuzz;

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CommuteTypeMutation implements BigFuzzMutation {

    Random r = new Random();
    ArrayList<String> fileRows = new ArrayList<String>();
    String delete;

    @Override
    public void mutate(String inputFile, String nextInputFile) throws IOException {
        List<String> fileList = Files.readAllLines(Paths.get(inputFile));
        Random random = new Random();
        int n = random.nextInt(fileList.size());
        String fileToMutate = fileList.get(n);
        mutateFile(fileToMutate, n);

        String fileName = nextInputFile + "+" + fileToMutate.substring(fileToMutate.lastIndexOf('/')+1);
        writeFile(fileName);

        String path = System.getProperty("user.dir")+"/"+fileName;
//        System.out.println(path);
//        System.out.println(fileList);

        delete = path;
        // write next input config
        BufferedWriter bw = new BufferedWriter(new FileWriter(nextInputFile));

        for(int i = 0; i < fileList.size(); i++)
        {
            if(i == n)
                bw.write(path);
            else
                bw.write(fileList.get(i));
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }

    public void mutateFile(String inputFile, int index) throws IOException{
        switch(index)
        {
            case 0 :
                mutateFile(inputFile);
                break;

            case 1 :
                mutateFile1(inputFile);
                break;
            default :
                mutateFile(inputFile);
        }
    }

    public void mutateFile(String inputFile) throws IOException
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

    public void mutateFile1(String inputFile) throws IOException
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

        mutate1(rows);

        fileRows = rows;
    }

    @Override
    public void mutate(ArrayList<String> list) {
        r.setSeed(System.currentTimeMillis());
        int lineNum = r.nextInt(list.size());
        // 0: random change value
        // 1: random change into float
        // 2: random insert
        // 3: random delete one column
        // 4: random add one coumn
        int method = r.nextInt(5);


//        if (method ==0) {
//            randomChangeByte(list.get(lineNum));
//        }
//        else {
        String[] columns = list.get(lineNum).split(",");
        int columnID = r.nextInt(5);

        System.out.println("***************"+method +" " + columnID);

        if(method == 0){
            columns[columnID] = Integer.toString(r.nextInt());
        }
        else if(method==1) {
            int value = 0;
            value = Integer.parseInt(columns[columnID]);
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
        if(columns.length == 0) line = columns[0];
        else {
            for(int j=0;j<columns.length;j++) {
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

    public void mutate1(ArrayList<String> list)
    {
        r.setSeed(System.currentTimeMillis());
        int lineNum = r.nextInt(list.size());
        // 0: random change value
        // 1: random insert
        // 2: random delete one column
        // 3: random add one coumn
        String[] columns = list.get(lineNum).split(",");
        int method = r.nextInt(4);
        int columnID = r.nextInt(2);
        System.out.println("***************"+method +" " + columnID);

        if(method == 0){
            columns[columnID] = Integer.toString(r.nextInt());
        }
        else if(method==1) {
            char temp = (char)r.nextInt(256);
            int pos = r.nextInt(columns[columnID].length());
            columns[columnID] = columns[columnID].substring(0, pos)+temp+columns[columnID].substring(pos);
        }
        else if(method==2) {
            columns = removeOneElement(columns, columnID);
        }
        else if(method==3) {
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

    @Override
    public void randomDuplicateRows(ArrayList<String> rows) {

    }

    @Override
    public void randomGenerateRows(ArrayList<String> rows) {

    }

    @Override
    public void randomGenerateOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows) {

    }

    @Override
    public void randomDuplacteOneColumn(int columnID, int intV, int maxV, ArrayList<String> rows) {

    }

    @Override
    public void improveOneColumn(int columnID, int intV, int maxV, ArrayList<String> rows) {

    }

    @Override
    public void writeFile(String outputFile) throws IOException {
        File fout = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < fileRows.size(); i++) {
            bw.write(fileRows.get(i));
            bw.newLine();
        }

        bw.close();
    }

    @Override
    public void deleteFile(String currentFile) throws IOException {
        File del = new File(delete);
        del.delete();
    }
}
