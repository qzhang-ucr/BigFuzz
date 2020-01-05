import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UDFgenerator {
    private String pathw = "customarray/src/edu/ucla/cs/bigfuzz/customarray/";
    FileWriter CustomFile;
    String name;
    public UDFgenerator(String name) throws IOException {
        CustomFile = new FileWriter(pathw+name+"CustomArray.java");
        this.name =name;
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

    public void UDFset(ArrayList<String> dataFlow) throws IOException {
        CustomFile.write(UDFPreConstruct()+"\n");
        for (String operator: dataFlow){
            if (operator!="CustomArray.read(inputFile);") {CustomFile.write(" public static ArrayList<Object> "+operator+"(ArrayList<Object> lines)\n"+"{\n"+
                    "        int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();\n" +
                    "\n" +
                    "        int iid = CustomArray.class.hashCode(); // this should be a random value associated with a program location\n" +
                    "        MemberRef method = new METHOD_BEGIN(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), \"()V\"); // containing method\n" +
                    "\n" +
                    "        // Generate a custom event!\n" +
                    "        TraceLogger.get().emit(new MapValuesEvent(iid, method, callersLineNumber));"+"ArrayList<Object> k = new ArrayList<>();\n"+"return k;\n"+"\n}\n");
        }
        }
        CustomFile.write("}");
        CustomFile.close();
    }

}
