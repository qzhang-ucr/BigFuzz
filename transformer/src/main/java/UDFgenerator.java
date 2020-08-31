import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//can't support two input argument for one UDF,
public class UDFgenerator {
    private String pathw = "customarray/src/edu/ucla/cs/bigfuzz/customarray/";
    private String pathr = "customarray/src/edu/ucla/cs/bigfuzz/sparkprogram";
    FileWriter CustomFile;
    String name;
    String UDFfileName;
    static boolean[] used;
    public UDFgenerator(String name, int number) throws IOException {
        CustomFile = new FileWriter(pathw+name+"CustomArray.java");
        this.name =name;
        used = new boolean[1000];
        Arrays.fill(used,Boolean.TRUE);
    }
    private String UDFPreConstruct() {
        String head = "package edu.ucla.cs.bigfuzz.customarray;\n" +
                "\n" +
                "import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;\n" +
                "import edu.ucla.cs.bigfuzz.dataflow.*;\n" +
                "import janala.logger.inst.METHOD_BEGIN;\n" +
                "import janala.logger.inst.MemberRef;\n" +
                "import javafx.util.Pair;\n" +
                "\n" +
                "import java.io.BufferedReader;\n" +
                "import java.io.FileReader;\n" +
                "import scala.*;\n" +
                "import java.util.*;"+
                "public class "+name+"CustomArray {\n" +
                "\n" +
                "    private ArrayList<Object> list;\n" +
                "\n" +
                "    public void "+name+"CustomArray() {\n" +
                "\n" +
                "    }";
        return head;
    }
    private boolean CheckSameOperator(String operator, String fileName)
    {
        String temp = fileName.substring(0,1).toUpperCase()+fileName.substring(1);
        //int i = Integer.parseInt(fileName.substring(fileName.length()-5));
        if (temp.length()<6) return false;
        int kk = temp.length()-6;
        while (temp.charAt(kk)>='0'&&temp.charAt(kk)<='9') kk=kk-1;
        temp = temp.substring(0,kk+1);
        //int min = 99999;
        if (temp.equals(operator.substring(0,operator.length()-1))) {
            int i = Integer.parseInt(fileName.substring(kk+1,fileName.length()-5));
            //used[i] = false;
            return used[i];
        }
        return false;
    }


    private String UDFreader(String operator) throws IOException {

        String inputFile = pathw+UDFget(operator);
        File file = new File(inputFile);
        //ArrayList<String> list = new ArrayList<>();
        Pattern Main = Pattern.compile("main");
        Matcher Ma;
        Pattern type = Pattern.compile("static|final");
        Matcher m;
        if (file.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String readline;
            while ((readline = br.readLine()) != null)
            {
                m = type.matcher(readline);
                Ma = Main.matcher(readline);
                if (m.find()&&!Ma.find()) return readline;
            }
        }
        return "";
        //return list;
    }

    private String UDFget(String operator) throws FileNotFoundException {
        File dir = new File(pathw);

        String [] UDFnames = dir.list();
        String ans ="";

        int k = 0;
        int kk=0;

        assert UDFnames != null;
        for (String UDF: UDFnames){
            if (CheckSameOperator(operator,UDF)) {
                kk =UDF.length()-6;
                while (UDF.charAt(kk)>='0'&&UDF.charAt(kk)<='9') kk=kk-1;
                int i = Integer.parseInt(UDF.substring(kk+1,UDF.length()-5));
                if (i>k)
                {
                    k = i;
                    ans = UDF;
                    UDFfileName=UDF;
                }
            }

        }
        if (k>0)
        {
            used[k] = false;
            //System.out.println(ans);
            return ans;
        }
        System.out.println(operator+":"+kk);
        System.out.println("Error: Not find UDF");
        return "error";
    }

    private String checkOutType(String typeLine) {

        Pattern type = Pattern.compile("apply");
        Matcher m = type.matcher(typeLine);
        m.find();
        int start = m.start()-2;
        while (typeLine.charAt(start)!=' ') {start=start-1;}

        return typeLine.substring(start,m.start()-1);

    }

    private String checkName(String typeLine) throws IOException {

        //String typeLine = UDFreader(fileName);
        Pattern type = Pattern.compile("apply");
        Matcher m = type.matcher(typeLine);
        m.find();
        int end = m.end()-1;
        while (typeLine.charAt(end)!='(') {end=end+1;}

        String importFile = UDFfileName.substring(0,UDFfileName.length()-5);

        return importFile+"."+typeLine.substring(m.start(),end);

    }

    public ArrayList<String> UDFset(ArrayList<String> dataFlow) throws IOException {
        CustomFile.write(UDFPreConstruct()+"\n");
        ArrayList<String> type = new ArrayList<>();
        String last = "";
        for (String operator: dataFlow){
            if ((!operator.equals("CustomArray.read(inputFile);"))&&(!operator.equals("CustomArray.readStr(inputFile);"))) {

                String Line = UDFreader(operator);
                //System.out.println(UDFfileName);
                String s = checkOutType(Line);
                boolean filterFlag = operator.substring(0, operator.length() - 1).equals("Filter");
                boolean reduceByKeyFlag= operator.substring(0,operator.length()-1).equals("ReduceByKey");
                if (filterFlag || reduceByKeyFlag) {s = last;}
                type.add(s);

                int flagFlatMapLast = 1;
                String finalLine = "";
                if (last.equals(" String[]")){
                    finalLine = "for ( String[] results: result){for(String R: results) {ans.add("+checkName(Line)+"( R));}}\n";
                }
                else {
                    finalLine = "for (" + last + " results: result)" + "{ans.add( "+ checkName(Line) + "( results));}\n";
                }
                if ((!filterFlag)&(!reduceByKeyFlag)){
                CustomFile.write(" public static ArrayList<" +
                        s+ "> " + operator + "(ArrayList<" + last + "> result)" + "{\n" +

                        "        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();\n" +
                        "\n" +
                        "        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a " +
                        "program location\n" +
                        "        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassN" +
                        "ame(), Thread.currentThread().getStackTrace()[1].getMethodName(), \"()V\"); // containing method\n" +
                        "\n" +
                        "        ArrayList<" + s + "> ans = new ArrayList<>();\n" +
                        "        TraceLogger.get().emit(new " + operator.substring(0, operator.length() - 1) +
                        "Event(iid, m" +
                        "ethod, callersLineNumber));\n" + finalLine + "return ans;\n}\n"
                );}

                else if (filterFlag){
                    CustomFile.write(" public static ArrayList<" +
                            s+ "> " + operator + "(ArrayList<" + last + "> result)" + "{\n" +

                            "        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();\n" +
                            "\n" +
                            "        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a " +
                            "program location\n" +
                            "        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassN" +
                            "ame(), Thread.currentThread().getStackTrace()[1].getMethodName(), \"()V\"); // containing method\n" +
                            "\n" +
                            "        ArrayList<" + s + "> ans = new ArrayList<>();\n"
                            + "for (" + last + " results: result)" + "{if ("+ checkName
                            (Line) + "(results._1(),results._2()))" +
                            "ans.add(results );}\n" + "int arm = 0;\n" +
                            "if (!ans.isEmpty()) arm=1;\n" +
                            "     TraceLogger.get().emit(new FilterEvent(iid, method, callersLineNumber,arm));\n" +
                            "return ans;\n}\n"
                    );}

                else {
                    CustomFile.write(" public static ArrayList<" +
                            s+ "> " + operator + "(ArrayList<" + last + "> result)" + "{\n" +

                            "        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();\n" +
                            "\n" +
                            "        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a " +
                            "program location\n" +
                            "        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassN" +
                            "ame(), Thread.currentThread().getStackTrace()[1].getMethodName(), \"()V\"); // containing method\n" +
                            "        TraceLogger.get().emit(new ReduceByKeyEvent(iid, method, callersLineNumber));\n"+
                            "        ArrayList<"+s+"> ans =new ArrayList<>();\n" +
                            "        int[][] array;  //prepare for reduce by key, array[][] records number list\n" +
                            "        int[] num; // num[] records how many same item for one specific item\n" +
                            "        num = new int [result.size()+1];\n" +
                            "        array = new int [result.size()+1][result.size()+1];\n" +
                            "        ArrayList<"+s+"> results55 = new ArrayList<>();\n" +
                            "        for ("+s+" result1: result){\n" +
                            "            int a = 0; //flag\n" +
                            "            for ("+s+" result2: results55){\n" +
                            "                if (result1._1().equals(result2._1())) {\n" +
                            "                    num[a] = num[a] + 1;\n" +
                            "                    a = a - 99999;\n" +
                            "                    break;\n" +
                            "                }\n" +
                            "                a = a+1;\n" +
                            "            }\n" +
                            "            if (a>=0) {\n" +
                            "                array[results55.size()][num[results55.size()]] = result1._2();\n" +
                            "                num[results55.size()]=1;\n" +
                            "                results55.add(result1);\n" +
                            "            }\n" +
                            "            else {\n" +
                            "                array[a+99999][num[a+99999]] = result1._2();\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        int a=0;\n" +
                            "        for ("+s+" results: results55)\n" +
                            "        {\n" +
                            "            int k = 0;\n" +
                            "            for (int i=0;i<num[a];i++) {\n" +
                            "                k="+checkName(Line)+"(array[a]);\n" +
                            "            }\n" +
                            "            ans.add(new "+s+"(results._1(),k));\n" +
                            "            a=a+1;\n" +
                            "        }\n" +
                            "        return ans;\n" +
                            "    }");//not finished
                }

                last = s;

            }
            else {last = "String";}
        }
        CustomFile.write("}");
        CustomFile.close();
        return type;
    }

}
