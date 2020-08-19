import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToComplie {
    private static String[] constLibrary = new String[]{"Map","Filter",
            "MapValues","ReduceByKey","MapToPair","FlatMap",
            "CustomArray.read(inputFile);","Join","Reduce"};
    private static int[] timeForOpe = new int[]{0,0,0,0,0,0,0,0,0};
    private static int totalStage = 0;
    private static ArrayList<String> operatorLine = new ArrayList<>();





    private static ArrayList<String> reader(String inputFile) throws IOException {
        File file = new File(inputFile);
        ArrayList<String> list = new ArrayList<>();
        if (file.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String readline;
            while ((readline = br.readLine()) != null)
            {
                list.add(readline);
            }
        }
        return list;
    }


    private static ArrayList<String> Refactor(ArrayList<String> source, String name) {

        ArrayList<String> list = new ArrayList<>();
        list.add("import edu.ucla.cs.bigfuzz.customarray.*;");
        //list.add("import edu.ucla.cs.bigfuzz.customarray.CustomArray;");
        list.add("import javafx.util.Pair;");
        list.add("import scala.*;");
        list.add("import java.io.IOException;");
        list.add("import java.util.ArrayList;");
        list.add("import java.util.Map;");
        list.add("public class "+name+" {");
        list.add("public void "+name+"(String inputFile) throws IOException {");
        ComplieTool a = new ComplieTool();
        Pattern comment = Pattern.compile("^\\s+//|//");
        Matcher m;
        Pattern mai = Pattern.compile("def main");
        ArrayList<String> mapToVar = new ArrayList<>();
        int k=1;

        boolean findAlready;
        boolean main=false;

        for (String line: source){
            m = mai.matcher(line);
            if (m.find()) {main = true;}
            if (!main) {continue;}

            m = comment.matcher(line);
            if (m.find()) {
                continue;
            }
            ArrayList<Integer> thisLine = a.DataFlowSequence(line);

            findAlready= a.findVal(line) != null;
            for (Integer operator: thisLine)
            {

                timeForOpe[operator]=timeForOpe[operator]+1;
                String newLine;
                if (operator!=6) {
                    String previous;

                    if (findAlready){
                        previous = a.trace(mapToVar,line);
                        findAlready = false;
                        mapToVar.add(a.findVal(line));

                    }
                    else {
                        previous = "results"+(totalStage-1);

                        mapToVar.add(mapToVar.get(mapToVar.size()-1));
                    }
                    newLine = "ArrayList<> results"+totalStage+" = "+name+"CustomArray."+
                            constLibrary[operator]+timeForOpe[operator]+"("+previous+");";
                    operatorLine.add(constLibrary[operator]+timeForOpe[operator]);
                }
                else {
                    newLine = "ArrayList<String> results"+totalStage+" = "+constLibrary[operator];
                    mapToVar.add(a.findVal(line));
                    operatorLine.add(constLibrary[operator]);
                    findAlready = true;
                }
                list.add(newLine);
                totalStage = totalStage +1;
            }

        }
        list.add("}}");

        return list;
    }

    private static ArrayList<String> generateDrive(ArrayList<String> source, String name){
        ArrayList<String> list = new ArrayList<>();
        list.add("import edu.berkeley.cs.jqf.fuzz.Fuzz;\n" +
                "import edu.berkeley.cs.jqf.fuzz.JQF;\n" +
                "import org.junit.runner.RunWith;\n" +
                "\n" +
                "import java.io.IOException;\n" +
                "@RunWith(JQF.class)\n");

        list.add("public class "+name+"Driver {\n");
        list.add("@Fuzz");
        list.add("    public void test"+name+"(String fileName) throws IOException {");
        list.add("        System.out.println(\""+name+"Driver::test"+name+": \"+fileName);\n" +
                "        "+name+" analysis = new "+name+"();\n" +
                "        analysis."+name+"(fileName);\n" +
                "    }\n}");
        return list;
    }

    public static void main(String[] args) throws IOException {//the type check in main program has not been done yet
        ArrayList<String> sourceCode;//the filter, map, etc, can be done automatically but not finished yet
        //sourceCode = reader(args[0]);
        JadTransUDF jad = new JadTransUDF();

        String pathr = "customarray/src/edu/ucla/cs/bigfuzz/sparkprogram/";
        String pathw = "customarray/src/";
        String JadPath = pathw+"edu/ucla/cs/bigfuzz/customarray/";
        sourceCode = reader(pathr+"Combined.scala");
        //String name =args[1];
        String name = "Combined";
        //ArrayList<String> JadSourceCode = reader(JadPath+"Test2$.java");

        sourceCode = Refactor(sourceCode, name);
        UDFgenerator UDF = new UDFgenerator(name,operatorLine.size());
        ArrayList<String> type=UDF.UDFset(operatorLine);
        int k=0;

        ArrayList<String> driver;
        driver = generateDrive(sourceCode, name);
        FileWriter codeFile = new FileWriter(pathw+name+".java");
        FileWriter driveFile = new FileWriter(pathw+name+"Driver.java");
        for (String line: sourceCode){
            if (line.length()>10){
            String a=line.substring(0,11);
            System.out.println(line);
            if (a.equals("ArrayList<>")) {
                a=a.substring(0,9)+"<"+type.get(k)+">"+ line.substring(12);
                codeFile.write(a+"\n");
                k=k+1;
                System.out.println(a);
                }
            else codeFile.write(line+"\n");
            }
            else codeFile.write(line+"\n");
        }
        codeFile.close();
        for (String line: driver){
            driveFile.write(line+"\n");
        }
        driveFile.close();


        //jad.FindUDF(JadSourceCode,operatorLine);


    }
}