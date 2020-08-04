import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaUDF {
    String pathw ="customarray/src/edu/ucla/cs/bigfuzz/customarray/";

    private ArrayList<String> GeneratorUDF(int index, ArrayList<String> Source, String Operation){
        int s=0;
        ArrayList<String> UDFcode = new ArrayList<>();
        UDFcode.add("package edu.ucla.cs.bigfuzz.customarray;\n");
        UDFcode.add("public class "+Operation+"{");
        for (int i=index;i<Source.size();i++){
            for (int j=0;j<Source.get(i).length();j++){
                if (Source.get(i).charAt(j) == '{'){
                    s=s+1;
                }
                if (Source.get(i).charAt(j)=='}'){
                    s=s-1;
                }
            }
            UDFcode.add(Source.get(i));
            if (s<0) break;
        }
        return UDFcode;

    }

    public void FindUDF(ArrayList<String> Source, ArrayList<String> Operation) throws IOException {
        Pattern ForFind = Pattern.compile("@Override");
        Matcher m;

        int LineNumber = 0;
        int OperationNumber = 1;

        for (String line : Source){
            m = ForFind.matcher(line);
            if (m.find()){
                ArrayList<String> OperationNow = GeneratorUDF(LineNumber,Source,Operation.get(OperationNumber));
                FileWriter UDF = new FileWriter(pathw+Operation.get(OperationNumber)+".java");
                for (String lines : OperationNow){
                    UDF.write(lines+"\n");
                }
                UDF.close();
                OperationNumber=OperationNumber+1;

            }
            LineNumber=LineNumber+1;
        }
    }

}
