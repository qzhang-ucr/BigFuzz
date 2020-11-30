package edu.ucla.cs.jqf.bigfuzz;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.LinkedList;

public class MutationGenerator {

    public static void main(String[] args) throws IOException {
        //read data type file in
        String datafile = "/home/qzhang/Programs/BigFuzz/dataset/data";
        //TODO 1: get the # of columns and delimeter in data file
        FileInputStream inputStream = new FileInputStream(datafile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;

        int lineCount = 0;
        String cols = "";
        LinkedList<String> types = new LinkedList<String>();
        String delimeter = "";
        while((str = bufferedReader.readLine())!=null)
        {
            //System.out.println(str);
            if(lineCount==1)
            {
                cols = str;
            }
            else if(lineCount == 3)
            {
                String[] temp = str.split(" ");
                for(String t:temp)
                {
                    types.add(t);
                }
            }
            else if(lineCount == 5)
            {
                delimeter = str;
            }
            lineCount++;
        }

        inputStream.close();
        //copy SalaryAnalysisMutation.java
        //File src = new File("/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/MutationTemplate.java");
        //String fileName = "/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/CustomMutation.java";
        //File dst = new File(fileName);

        //TODO 2: change the Class Name to FindSalaryMutation
        String srcPath = "/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/MutationTemplate.java";
        String dstPath = "/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/CustomMutation.java";


        generateCustomedSourceFile(srcPath, dstPath, "CustomMutation", delimeter, cols);

    }

    /*private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }*/
    private static void generateCustomedSourceFile(String srcPath, String dstPath, String newClassName, String delimeter, String cols)
    {
        try
        {
            File file = new File(srcPath);
            Long filelength = file.length();
            byte[] buffer = new byte[filelength.intValue()];
            FileInputStream in = new FileInputStream(file);
            in.read(buffer);
            in.close();
            String content = new String(buffer);
            content = content.replace("MutationTemplate", newClassName);
            content = content.replace("$del$", delimeter);
            content = content.replace("$cols$", cols);
            File txt=new File(dstPath);
            if(!txt.exists()){
                txt.createNewFile();
            }
            byte bytes[]=new byte[512];
            bytes=content.getBytes();
            int b=bytes.length;
            FileOutputStream fos=new FileOutputStream(txt);
            fos.write(bytes,0,b);
            fos.close();

        }
        catch(IOException e)
        {

        }
    }
}
