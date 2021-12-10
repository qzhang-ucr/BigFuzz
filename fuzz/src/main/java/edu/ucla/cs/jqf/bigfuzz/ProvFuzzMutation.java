package edu.ucla.cs.jqf.bigfuzz;

//import org.apache.commons.lang.ArrayUtils;

/*
 mutation for I5: unsupported DF operator
 */

//import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProvFuzzMutation implements BigFuzzMutation{

    private ArrayList<Integer>[] nonFuzzCols = new ArrayList[5];
	private ArrayList<Integer>[] fuzzCols = new ArrayList[5];
	private ArrayList<String>[] seedInputs = new ArrayList[5];
	private ArrayList<String> fileRows = new ArrayList();
	private final int TYPE_STRING = 1;
	private final int TYPE_NUMERICAL = 2;
	//56226244,ComputerScience,female,Senior,24
	private ArrayList<String>[] schema = new ArrayList[]{
			new ArrayList(),
			new ArrayList(Arrays.asList(new String[]{"$$$$", "English", "Mathematics", "ComputerScience", "ElectricalEngineering", "Business", "Economics", "Biology", "Law", "PoliticalScience", "IndustrialEngineering"})),
			new ArrayList(Arrays.asList(new String[]{"$$$$", "male", "female"})),
			new ArrayList(Arrays.asList(new String[]{"$$$$", "Freshman", "Sophomore", "Junior", "Senior"})),
			new ArrayList()
	};
	private Random rand = new Random();
	private int maxDuplicatedTimes = 10;
	private int maxGenerateTimes = 20;
	private int maxSamples = 0;
	private int branch;

	public ProvFuzzMutation(String configFile, int branch) throws IOException {
		this.branch = branch;
		parseConfigFile(configFile);
	}

	public void randomDuplicateRows(ArrayList<String> rows)
	{
		int ind = rand.nextInt(rows.size());
		int duplicatedTimes = rand.nextInt(maxDuplicatedTimes)+1;
		String duplicatedValue = rows.get(ind);
		for(int i=0;i<duplicatedTimes;i++)
		{
			int insertPos = rand.nextInt(rows.size());
			rows.add(insertPos, duplicatedValue);
		}
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

    private void parseConfigFile(String filename) throws IOException {
		/*
		Format to parse:
			max_samples:5
			2:3:1,2
			56226244,ComputerScience,female,Senior,24
			99341171,ComputerScience,female,Senior,21
			8001804,ComputerScience,female,Freshman,15
			67561898,ComputerScience,female,Freshman,18
			79856355,ComputerScience,female,Sophomore,21
			56226244,ComputerScience,female,Senior,24
			99341171,ComputerScience,female,Senior,21
			8001804,ComputerScience,female,Freshman,15
			67561898,ComputerScience,female,Freshman,18
		 */
		List<String> configLines = Files.readAllLines(Paths.get(filename));
		this.maxSamples = Integer.parseInt(configLines.get(0).split(":")[1]);

		for(int b = 1; b < configLines.size(); b+=this.maxSamples*2+1) {
			String line =  configLines.get(b);
			final String[] branchInfo = line.split(":");
			int branchID = Integer.parseInt(branchInfo[0]);
			this.fuzzCols[branchID] = new ArrayList(Arrays.asList(branchInfo[1].split(",")));
			this.nonFuzzCols[branchID] = new ArrayList(Arrays.asList(branchInfo[2].split(",")));
			this.seedInputs[branchID] = new ArrayList(configLines.subList(b+1, b+this.maxSamples+1));
		}

		//Set the branch to fuzz here
		System.out.println("ProvFuzz :: Initialized Mutation Class");
		System.out.println("ProvFuzz :: Fuzzing branch #" + this.branch);
		System.out.println("ProvFuzz :: Fuzzing Columns " + this.fuzzCols[this.branch]);
		System.out.println("ProvFuzz :: Avoiding Columns " + this.nonFuzzCols[this.branch]);
		System.out.println("ProvFuzz :: Branch #"+this.branch+" Seed: " + this.seedInputs[this.branch]);
//		System.exit(1);


//	    this.maxCols = Integer.parseInt(configLines.get(0));
//	    ArrayList<Integer> nonFuzzCols = new ArrayList<Integer>();
//	    for(int i = 1; i < configLines.size(); i++){
//			nonFuzzCols.add(Integer.parseInt(configLines.get(i)));
//	    }
//	    this.nonFuzzCols = nonFuzzCols;
//		System.out.println("I AM PROVFUZZ:"+ this.nonFuzzCols);
//		System.exit(1);
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

	int min = 0;
	int max = fileList.size()-1;
	int n = random.nextInt(max - min + 1) + min;
	String fileToMutate = fileList.get(n);
	mutateFile(fileToMutate);
	String mutatedFileName = nextInputFile;// + "+" + fileToMutate.substring(fileToMutate.lastIndexOf('/')+1);
	writeFile(mutatedFileName);
    }

    @Override
    public void mutateFile(String inputFile, int index) throws IOException {
    }

    public void mutateFile(String inputFile) throws IOException {
//	File file = new File(inputFile);
	ArrayList<String> rows = (ArrayList<String>)this.seedInputs[this.branch].clone();
	System.out.println("SEED INPUT: " + this.seedInputs[this.branch]);
//	ArrayList<String> rows = new ArrayList<String>();
//	BufferedReader br = new BufferedReader(new FileReader(inputFile));
//
//	if(!file.exists()){
//		System.out.println("File " + inputFile + " doesn't exist");
//		System.exit(-1);
//	}
//	String readLine = null;
//	while((readLine = br.readLine()) != null){
//		rows.add(readLine);
//	}
//	br.close();

	//Manipulate rows here
	mutate(rows);
	//--------------------
	
	fileRows = rows;
	System.out.println("mutateFile: fileRows[0]: " + this.fileRows.get(0));
	System.out.println("mutateFile: nonFuzzCols: " + this.nonFuzzCols[this.branch]);
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

	private int schema_getType(int col) {
		if (Arrays.asList(new int[]{1,2,3}).contains(col)) {
			return this.TYPE_STRING;
		} else {
			return this.TYPE_NUMERICAL;
		}
	}
	private String M1(String value, int col) {
		if (schema_getType(col) == this.TYPE_STRING) {
			String new_val = this.schema[col].get(rand.nextInt(this.schema.length)-1);
			return new_val;
		} else if (schema_getType(col) == this.TYPE_NUMERICAL){
			return "," + "-1";
		}
		return ","+value;
	}
	private String M2(String value, int col) {
		try {
			int temp = Integer.parseInt(value);
			return ","+temp+".0";
		} catch (Exception e){
			return "," + value;
		}
	}
	private String M3(String value, int col) {
		return "~" + value;
	}
	private String M4(String value, int col) {
		return "," + value + "," + value;
	}
	private String M5(String value, int col) {
		return "";
	}
	private String M6(String value, int col) {
		return ",";
	}

	private String applyMutation(String value, int id, int col) {
		switch (id) {
			case 0:
				return M1(value, col);
			case 1:
				return M2(value, col);
			case 2:
				return M3(value, col);
			case 3:
				return M4(value, col);
			case 4:
				return M5(value, col);
			case 5:
				return M6(value, col);
			default:
				return "," + value;
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
			row += applyMutation(toMutate[i], (this.nonFuzzCols[this.branch].contains(""+i))? -1: mutationID, i);
		}

		list.set(toMutateIndex, row);
		System.out.println("mutate: mutated_row = " + row);
	}

    private String randomChangeByte(String instr)
    {
	    return "";
    }

}
