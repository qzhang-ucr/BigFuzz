package edu.ucla.cs.jqf.bigfuzz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MutationGenerator {

    public static void main(String[] args) throws IOException {
        //read data type file in
        String datafile = "/home/qzhang/Programs/BigFuzz/dataset/data";
        //get the # of columns and delimeter in data file

        //copy SalaryAnalysisMutation.java
        File src = new File("/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/SalaryAnalysisMutation.java");
        String fileName = "/home/qzhang/Programs/BigFuzz/fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/CustomMutation.java";
        File dst = new File(fileName);


        copyFileUsingFileChannels(src, dst);

    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
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
    }

}
