package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

/*
 mutation for I5: unsupported DF operator
 */

import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class InputReductionMutation implements BigFuzzMutation{

    private ArrayList<Integer> nonFuzzCols = new ArrayList<Integer>();
    private ArrayList<String> fileRows = new ArrayList<String>();
    private int maxCols;
    private String configFile = "/BigFuzz/dataset/ired/config.txt";


    public void randomDuplicateRows(ArrayList<String> rows)
    {
    }

    public void randomGenerateRows(ArrayList<String> rows)
    {
    }

    public void randomGenerateOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
    }

    public void randomDuplacteOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
    }

    public void improveOneColumn(int columnID, int minV, int maxV, ArrayList<String> rows)
    {
    }

    public void writeFile(String outputFile) throws IOException {
	    File fout = new File(outputFile);
	    FileOutputStream fos = new FileOutputStream(fout);
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

	    for(int i = 0; i < this.fileRows.size(); i++){
		    if(fileRows.get(i) == null) {
			    continue;
		    }
		    bw.write(fileRows.get(i));
		    bw.newLine();
	    }
	    bw.close();
	    fos.close();
    }

    public void deleteFile(String currentInputFile) throws IOException {
    }

    private void parseToInts(List<String> configLines){
	    this.maxCols = Integer.parseInt(configLines.get(0));
	    ArrayList<Integer> nonFuzzCols = new ArrayList<Integer>();
	    for(int i = 1; i < configLines.size(); i++){
		nonFuzzCols.add(Integer.parseInt(configLines.get(i)));
	    }
	    this.nonFuzzCols = nonFuzzCols;
    }

    public void mutate(String inputFile, String nextInputFile) throws IOException
    {
	    // inputFile format:
	    // 		path/to/data1.txt
	    // 		path/to/data2.txt
	    // configFile format (c1 etc are colums not to fuzz):
	    // 		[maxcols]
	    // 		[c1]
	    // 		[c2]
	    // 		...

	List<String> fileList = Files.readAllLines(Paths.get(inputFile));
	Random random = new Random();
	parseToInts(Files.readAllLines(Paths.get(this.configFile))); 

	int min = 0;
	int max = fileList.size();
	int n = random.nextInt(max - min + 1) + min;
	String fileToMutate = fileList.get(n);
	mutateFile(fileToMutate);
	String mutatedFileName = nextInputFile +
		"+" + fileToMutate.substring(fileToMutate.lastIndexOf('/')+1);
	writeFile(mutatedFileName);
    }

    @Override
    public void mutateFile(String inputFile, int index) throws IOException {
    }

    public void mutateFile(String inputFile) throws IOException {
	File file = new File(inputFile);
	ArrayList<String> rows = new ArrayList<String>();
	BufferedReader br = new BufferedReader(new FileReader(inputFile));
	
	if(!file.exists()){
		System.out.println("File " + inputFile + " doesn't exist");
		System.exit(-1);
	}
	String readLine = null;
	while((readLine = br.readLine()) != null){
		rows.add(readLine);
	}
	br.close();

	//Manipulate rows here
	
	//--------------------
	
	fileRows = rows;
	System.out.println(this.fileRows);
	System.out.println(this.nonFuzzCols);
    }
    public static String[] removeOneElement(String[] input, int index) {
	    return new String[1];
    }
    public static String[] AddOneElement(String[] input, String value, int index) {
	    return new String[1];
    }

    public void mutate(ArrayList<String> list)
    {
    }

    private String randomChangeByte(String instr)
    {
	    return "";
    }

}
