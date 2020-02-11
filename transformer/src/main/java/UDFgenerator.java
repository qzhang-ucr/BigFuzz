import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        used = new boolean[number];
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
                "import java.io.IOException;\n" +
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
        //System.out.println(fileName);
        //int i = Integer.parseInt(fileName.substring(fileName.length()-5));
        temp = temp.substring(0,temp.length()-6);
        if (temp.equals(operator.substring(0,operator.length()-1))) {
            int i = Integer.parseInt(fileName.substring(fileName.length()-6,fileName.length()-5));
            if (used[i]) {
                used[i] = false;
                return true;
            }
        }
        return false;
    }


    private String UDFreader(String operator) throws IOException {

        String inputFile = pathw+UDFget(operator);
        File file = new File(inputFile);
        //ArrayList<String> list = new ArrayList<>();
        Pattern Main = Pattern.compile("main");
        Matcher Ma;
        Pattern type = Pattern.compile("static");
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

        assert UDFnames != null;
        for (String UDF: UDFnames){
            if (CheckSameOperator(operator,UDF)) {
                UDFfileName = UDF;
                return UDF;
            }

        }
        System.out.println("Error: Not find UDF");
        return "error";
    }

    private String checkOutType(String typeLine) throws IOException {

        Pattern type = Pattern.compile("apply");
        Matcher m = type.matcher(typeLine);
        m.find();
        System.out.println(typeLine);
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

    private String checkInType(String typeLine) throws IOException {

        //String typeLine = UDFreader(fileName);
        Pattern type = Pattern.compile("apply");
        Matcher m = type.matcher(typeLine);
        m.find();
        int start = m.end()-1;
        while (typeLine.charAt(start)!='(') {start=start+1;}
        int end = start;
        while (typeLine.charAt(end)!=')') {end=end+1;}

        return typeLine.substring(start,end+1);

    }

    private String checkInVal(String typeLine) {
        Pattern type = Pattern.compile("apply");
        Matcher m = type.matcher(typeLine);
        m.find();
        int end = m.end()-1;
        while (typeLine.charAt(end)!=')') {end=end+1;}
        end=end-1;
        while (typeLine.charAt(end)==' ') {end=end-1;}
        int start = end;
        while (typeLine.charAt(start)!=' ') {start = start-1;}
        System.out.println(typeLine.substring(start,end));
        return typeLine.substring(start,end+1);
    }

    public void UDFset(ArrayList<String> dataFlow) throws IOException {
        CustomFile.write(UDFPreConstruct()+"\n");
        for (String operator: dataFlow){
            if ((!operator.equals("CustomArray.read(inputFile);"))&&(!operator.equals("CustomArray.readStr(inputFile);"))) {

                String Line = UDFreader(operator);

                CustomFile.write(" public static " +
                    checkOutType(Line)+" "+operator+checkInType(Line)+"{\n"+

                    "        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();\n" +
                    "\n" +
                    "        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a " +
                    "program location\n" +
                    "        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassN" +
                    "ame(), Thread.currentThread().getStackTrace()[1].getMethodName(), \"()V\"); // containing method\n" +
                    "\n" +
                    "        // Generate a custom event!\n" +
                    "        TraceLogger.get().emit(new "+operator.substring(0,operator.length()-1)+"Event(iid, m" +
                    "ethod, callersLineNumber));"+"return "+checkName(Line)+"("+checkInVal(Line)+");\n"+"\n}\n"
            );/////还没有加最后的引用部分

        }
        }
        CustomFile.write("}");
        CustomFile.close();
    }

}
