package edu.ucla.cs.jqf.bigfuzz;

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class CommuteTypeMutation implements BigFuzzMutation {

    Random r = new Random();
    ArrayList<String> fileRows = new ArrayList<String>();
    String delete;
    int maxGenerateTimes = 20;

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

        int method =(int)(Math.random() * 2);
        if(method == 0){
            ArrayList<String> tempRows = new ArrayList<String>();
            randomGenerateRows1(tempRows);
            System.out.println("rows: " + tempRows);
            rows = tempRows;

            int next =(int)(Math.random() * 2);
            if(next == 0){
                mutate1(rows);
            }
        }else{
            mutate1(rows);
        }

        fileRows = rows;
    }

    @Override
    public void mutate(ArrayList<String> list) {
        r.setSeed(System.currentTimeMillis());
        System.out.println(list.size());
        int lineNum = r.nextInt(list.size());
        System.out.println(list.get(lineNum));
        // 0: random change value
        // 1: random change into float
        // 2: random insert
        // 3: random delete one column
        // 4: random add one coumn
        String[] columns = list.get(lineNum).split(",");
        int method = r.nextInt(2);
        int columnID = (int)(Math.random() * columns.length);
        System.out.println("********"+method+" "+lineNum+" "+columnID);

        if(method==0) {
            String r = RandomStringUtils.randomAscii((int)(Math.random() * 5));
            columns[columnID] = r;
        }
        else if(method==1) {
            columns = removeOneElement(columns, columnID);
        }

        String line = "";
        int delimeter = r.nextInt(2);
        for(int j=0;j<columns.length;j++) {
            if(j==0)
            {
                line = columns[j];
            }
            else
            {
                if(delimeter == 0){
                    line = line+","+columns[j];
                }else{
                    line = line + "#" + columns[j];
                }
            }
        }
        list.set(lineNum, line);
    }

    public void mutate1(ArrayList<String> list) {
        r.setSeed(System.currentTimeMillis());
        System.out.println(list.size());
        int lineNum = r.nextInt(list.size());
        System.out.println(list.get(lineNum));
        // 0: random change value
        // 1: random change into float
        // 2: random insert
        // 3: random delete one column
        // 4: random add one coumn
        String[] columns = list.get(lineNum).split(",");
        int method = r.nextInt(2);
        int columnID = (int)(Math.random() * columns.length);
        System.out.println("********"+method+" "+lineNum+" "+columnID);

        if(method==0) {
            String r = RandomStringUtils.randomAscii((int)(Math.random() * 5));
            columns[columnID] = r;
        }else if(method==1) {
            columns = removeOneElement(columns, columnID);
        }

        String line = "";
        int delimeter = r.nextInt(2);
        for(int j=0;j<columns.length;j++) {
            if(j==0)
            {
                line = columns[j];
            }
            else
            {
                if(delimeter == 0){
                    line = line+","+columns[j];
                }else{
                    line = line + "#" + columns[j];
                }
            }
        }
        list.set(lineNum, line);
    }

    public static String[] removeOneElement(String[] input, int index) {
        List<String> list1 = Arrays.asList(input);
        List<String> arrList = new ArrayList<String>(list1);
        arrList.remove(input[index]);
        return arrList.toArray(new String[arrList.size()]);
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

    @Override
    public void randomDuplicateRows(ArrayList<String> rows) {

    }

    @Override
    public void randomGenerateRows(ArrayList<String> rows) {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        for(int i=0;i<generatedTimes;i++) {
            String numberAsString = new String();
            Integer index = i + 1;
            String zip1 = "9" + "0" + "0" + r.nextInt(10) + r.nextInt(10);
            String zip2 = "9" + "0" + "0" + r.nextInt(10) + r.nextInt(10);
            String dis = RandomStringUtils.randomNumeric((int) (Math.random() * 2));
            String time = RandomStringUtils.randomNumeric((int) (Math.random() * 4));
            numberAsString = index +","+zip1 + "," + zip2 + "," + dis + "," + time;
            rows.add(numberAsString);
        }
    }

    public void randomGenerateRows1(ArrayList<String> rows) {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;
        for(int i=0;i<generatedTimes;i++)
        {
            String numberAsString = new String();
            String zip = "9" + "0"+ "0" + r.nextInt(10) + r.nextInt(10);
            String name = RandomStringUtils.randomAlphabetic((int)(Math.random() * 5));
            numberAsString = zip + "," + name;
            rows.add(numberAsString);
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
