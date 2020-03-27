//import edu.berkeley.cs.jqf.fuzz.Fuzz;
//import edu.berkeley.cs.jqf.fuzz.JQF;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@RunWith(JQF.class)
//public class WordCountDriver {
//
//    @Fuzz
//    public void testWordCount(String fileName) throws IOException {
////        System.out.println(fileName);
////        byte[] bs = fileName.getBytes();
////        System.out.println(Arrays.toString(bs));
//        byte[] bytes = fileName.getBytes();
//        for(int i=0;i<bytes.length;i++)
//        {
//            System.out.println("WordCountDriver: i, b: "+i+", "+bytes[i]);
//        }
//        System.out.println("WordCountDriver::testWordCount:fileName: "+fileName);
//        WordCount cnt = new WordCount();
//        cnt.WordCount(fileName);
//    }


//
//    public static void main(String[] args) throws IOException {
////        if (args.length == 0) {
////            System.out.println("No files provided.");
////            System.exit(0);
////        }
////        WordCount cnt = new WordCount();
//        WordCount cnt = new WordCount();
////        cnt.rWordCount("/home/qzhang/Downloads/input");
//        cnt.WordCount("Apache Spark has a useful command prompt interface but its true power comes from complex data pipelines that are run non-interactively. Implementing such pipelines can be a daunting task for anyone not familiar with the tools used to build and deploy application software. This article is meant show all the required steps to get a Spark application up and running, including submitting an application to a Spark cluster.");
////        byte[] bs = "/home/qzhang/Downloads/input".getBytes();
////        System.out.println(Arrays.toString(bs));
//    }
//    //System.out.println("Number of lines in file = " + stringJavaRDD.count());

//}


import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@RunWith(JQF.class)
public class WordCountDriver {

    @Fuzz
    public void testWordCount(String fileName) throws Exception {
        System.out.println("edu.ucla.cs.bigfuzz.customarray.applicable.WordCount.WordCountDriver::testWordCount: "+fileName);

        List<String> fileList = Files.readAllLines(Paths.get(fileName));

        try {
            Scanner sc = new Scanner(new File(fileList.get(0)));

            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());

            }
        } catch (FileNotFoundException e) {

        }

        WordCount analysis = new WordCount();
        analysis.WordCount(fileList.get(0));
    }
}