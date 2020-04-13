package edu.ucla.cs.jqf.bigfuzz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestMutation {
    public static void main(String[] args) throws IOException
    {
        String initialInputFile = "/home/qzhang/Programs/BigFuzz/dataset/config";
        BigFuzzMutation mutation = new RandomMutation();

        String currentInputFile;
        int i = 123;

        String nextInputFile = new SimpleDateFormat("yyyyMMddHHmmss'_"+i+"'").format(new Date());
//        String fileName = nextInputFile.substring(0, nextInputFile.indexOf("."));

        mutation.mutate(initialInputFile, nextInputFile);//currentInputFile

        currentInputFile = nextInputFile;
//        System.out.println(currentInputFile);
//        mutation.writeFile(fileName);
    }

}
