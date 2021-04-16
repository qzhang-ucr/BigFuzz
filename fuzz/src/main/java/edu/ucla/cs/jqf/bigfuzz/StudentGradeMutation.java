package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class StudentGradeMutation implements BigFuzzMutation{

    Random r = new Random();
    ArrayList<String> fileRows = new ArrayList<String>();
    String delete;
    int maxGenerateTimes = 5;


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

    public void mutate(ArrayList<String> list)
    {
        r.setSeed(System.currentTimeMillis());
        System.out.println("list size: " + list.size());
        int lineNum = r.nextInt(list.size());
        System.out.println("original line: " + list.get(lineNum));

        if(list.get(lineNum).isEmpty()) return;
        int method =(int)(Math.random() * 3);
//        System.out.println("select method:" + method);
        if(method == 0){
            String[] first = list.get(lineNum).split(",");

            int firstID = (int)(Math.random() * first.length);
            String[] columns = first[firstID].split(":");
            int columnID = (int)(Math.random() * 2);
            columns = removeOneElement(columns,columnID);
            int delimeter =(int)(Math.random() * 2);
            if(delimeter == 0){
                if(columnID == 0) first[firstID] = ":" + columns[0];
                else if(columnID == 1) first[firstID] = columns[0] + ":";
            }else if(delimeter == 1){
                if(columnID == 0) first[firstID] = "#" + columns[0];
                else if(columnID == 1) first[firstID] = columns[0] + "#";
            }

            String line = "";
            for(int j=0;j<first.length;j++) {
                if(j==0)
                {
                    line = line + first[j];
                }
                else
                {
                    line = line+","+first[j];
                }
            }

            System.out.println("deleting column,,,,,,,,," + line);
            list.set(lineNum, line);
        }
        else if(method == 1){
            String[] first = list.get(lineNum).split(",");

            int firstID = (int)(Math.random() * first.length);
            String[] columns = first[firstID].split(":");
            columns[1] = RandomStringUtils.randomAscii(2);
            int delimeter =(int)(Math.random() * 2);
            if(delimeter == 0){
                first[firstID] = columns[0] + ":" + columns[1];
            }else if(delimeter == 1){
                first[firstID] = columns[0] + "#" + columns[1];
            }

            String line = "";
            for(int j=0;j<first.length;j++) {
                if(j==0)
                {
                    line = line + first[j];
                }
                else
                {
                    line = line+","+first[j];
                }
            }

            System.out.println("random change column ,,,,,,,,," + line);
            list.set(lineNum, line);
        }
        else if(method == 2){
            String[] first = list.get(lineNum).split(",");

            int firstID = (int)(Math.random() * first.length);
            String[] columns = first[firstID].split(":");
            int columnID = (int)(Math.random() * 2);
            columns[columnID] = "";
            int delimeter =(int)(Math.random() * 2);
            if(delimeter == 0){
                if(columnID == 0) first[firstID] = ":" + columns[0];
                else if(columnID == 1) first[firstID] = columns[0] + ":";
            }else if(delimeter == 1){
                if(columnID == 0) first[firstID] = "#" + columns[0];
                else if(columnID == 1) first[firstID] = columns[0] + "#";
            }


            String line = "";
            for(int j=0;j<first.length;j++) {
                if(j==0)
                {
                    line = line + first[j];
                }
                else
                {
                    line = line+","+first[j];
                }
            }
            System.out.println("random empty column ,,,,,,,,,,,,,,,," + line);
            list.set(lineNum, line);
        }
    }

    @Override
    public void randomDuplicateRows(ArrayList<String> rows) {

    }

    @Override
    public void randomGenerateRows(ArrayList<String> rows) {
        int generatedTimes = r.nextInt(maxGenerateTimes)+1;

        for(int i=0;i<generatedTimes;i++)
        {
            int numberInRow = (int)(Math.random() * 5);
//            int numberInRow = 1;
            String numberAsString = new String();
            for (int j = 0; j < numberInRow; j++){
                String course= Integer.toString(r.nextInt(10) + 1);
                String dep = new String();
                if(r.nextBoolean()){
                    dep = "EE";
                }else dep = "CS";
                String grade = Integer.toString(r.nextInt(90)+10);

                numberAsString = dep + course + ":" + grade + numberAsString;
                if(j < (numberInRow-1)) numberAsString = "," + numberAsString;
            }
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
