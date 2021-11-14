package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

/*
 mutation for I5: unsupported DF operator
 */

//import org.apache.commons.lang.RandomStringUtils;

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
    private String configFile = "/home/ahmad/Documents/VT/project1/BigFuzz/dataset/ired/config.txt";
	private Random rand = new Random();


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
	System.out.println("mutate: " + fileList);
	Random random = new Random();
	parseToInts(Files.readAllLines(Paths.get(this.configFile))); 

	int min = 0;
	int max = fileList.size()-1;
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
	mutate(rows);
	//--------------------
	
	fileRows = rows;
	System.out.println("mutateFile: fileRows[0]: " + this.fileRows.get(0));
	System.out.println("mutateFile: nonFuzzCols: " + this.nonFuzzCols);
    }
    public static String[] removeOneElement(String[] input, int index) {
	    return new String[1];
    }
    public static String[] AddOneElement(String[] input, String value, int index) {
	    return new String[1];
    }

	private int genValidColIndex(Random rand, int max, ArrayList<Integer> exceptions) {
		int col;
		while (true) {
			col = rand.nextInt(max);
			if (!exceptions.contains(col)) {
				return col;
			}
		}
	}

	private String M1(String value) {
		return "," + value;
	}
	private String M2(String value) {
		return "," + value;
	}
	private String M3(String value) {
		return "," + value;
	}
	private String M4(String value) {
		return "," + value;
	}
	private String M5(String value) {
		return "," + value;
	}
	private String M6(String value) {
		return "," + value;
	}

	private String applyMutation(String value, int id) {
		switch (id) {
			case 0:
				return M1(value);
			case 1:
				return M2(value);
			case 2:
				return M3(value);
			case 3:
				return M4(value);
			case 4:
				return M5(value);
			case 5:
				return M6(value);
			default:
				return ",<" + value + ">";
		}
	}

    public void mutate(ArrayList<String> list) {
		long seed = System.currentTimeMillis();
		this.rand.setSeed(seed);
		int toMutateIndex = this.rand.nextInt(list.size()); // pick a random row to mutate
		String[] toMutate = list.get(toMutateIndex).split(",");
		System.out.println("mutate: Mutating row " + toMutateIndex + ", seed = " + seed);
		System.out.println("mutate: row = " + list.get(toMutateIndex));

		// int mutateCol = genValidColIndex(this.rand, this.maxCols, this.nonFuzzCols);
		// javac -cp .:$(~/Documents/VT/project1/BigFuzz/scripts/classpath.sh) BigFuzz* InputRed* -d ~/Documents/VT/project1/BigFuzz/fuzz/target/classes
		//~/Documents/VT/project1/BigFuzz/bin/jqf-bigfuzz -c ~/Documents/VT/project1/BigFuzz/customarray/target/classes:$(~/Documents/VT/project1/BigFuzz/scripts/classpath.sh) MatrixMinMaxNNDriver testMatrixMinMaxNN 10

		String row = toMutate[0];
		for (int i = 1; i < toMutate.length; i++){
			int mutationID = this.rand.nextInt(6);
			row += applyMutation(toMutate[i], (this.nonFuzzCols.contains(i))? -1: mutationID);
		}

		list.set(toMutateIndex, row);
		System.out.println("mutate: mutated_row = " + row);
	}

    private String randomChangeByte(String instr)
    {
	    return "";
    }

}
