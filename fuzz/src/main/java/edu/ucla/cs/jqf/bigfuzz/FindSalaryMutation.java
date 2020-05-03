package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FindSalaryMutation implements BigFuzzMutation{

    Random r = new Random();
    ArrayList<String> fileRows = new ArrayList<String>();
    String delete;
    int maxGenerateTimes = 10;


    public void mutate(String inputFile, String nextInputFile) throws IOException
    {
        List<String> fileList = Files.readAllLines(Paths.get(inputFile));
        Random random = new Random();
        int n = random.nextInt(fileList.size());
        String fileToMutate = fileList.get(n);
        mutateFile(fileToMutate);

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

    @Override
    public void mutateFile(String inputFile, int index) throws IOException {

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

        br.close();

        int method =(int)(Math.random() * 2);
        if(method == 0){
            ArrayList<String> tempRows = new ArrayList<String>();
            randomGenerateRows(tempRows);
            System.out.println("rows: " + tempRows);
            rows = tempRows;

            int next =(int)(Math.random() * 2);
            if(next == 0){
                mutate(rows);
            }
        }else{
            mutate(rows);
        }

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
        System.out.println(list.size());
        int lineNum = r.nextInt(list.size());
        System.out.println(list.get(lineNum));
//        // 0: random change value
        // 1: random change into string
//        // 2: random insert
        // 3: random delete one column
        // 4: random add one coumn
        String[] columns = list.get(lineNum).split(",");
        int method = r.nextInt(2);
        int columnID = r.nextInt(Integer.parseInt("1"));

        if(method==0) {
            if( columns[columnID] == "") return;

            int next = r.nextInt(2);
            if(next == 0){
                columns[columnID] = "$" + RandomStringUtils.randomAscii((int)(Math.random() * 10));
            } else{
                columns[columnID] = RandomStringUtils.randomAscii((int)(Math.random() * 10));
            }
//            if(columns[columnID].charAt(0)=='$')
//            {
//                columns[columnID] = "$" + RandomStringUtils.randomAscii((int)(Math.random() * 10));
//            }
//            else
//            {
//                columns[columnID] = RandomStringUtils.randomAscii((int)(Math.random() * 6));
//            }
        }
        else if(method==1) {
            columns = removeOneElement(columns, columnID);
        }
//        else if(method==2) {
//            String one = Integer.toString(r.nextInt(10000));
//            columns = AddOneElement(columns, one, columnID);
//        }
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

    @Override
    public void randomDuplicateRows(ArrayList<String> rows) {

    }

    @Override
    public void randomGenerateRows(ArrayList<String> rows) {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        for(int i=0;i<generatedTimes;i++)
        {
            int bits = (int)(Math.random()*6);
            String tempRow = RandomStringUtils.randomNumeric(bits);
            int method =(int)(Math.random() * 2);
            if(method == 0){
                int next = (int)(Math.random()*2);
                if(next == 0) {
                    rows.add("$" + tempRow);
                }else {
                    rows.add(tempRow);
                }
            }
            else{
                rows.add(RandomStringUtils.randomNumeric(3));
            }
        }
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
            if(fileRows.get(i) == null) {
                continue;
            }
            bw.write(fileRows.get(i));
            bw.newLine();
        }

        bw.close();
        fos.close();
    }

    @Override
    public void deleteFile(String currentFile) throws IOException {
        File del = new File(delete);
        del.delete();
    }

}
