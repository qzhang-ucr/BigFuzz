import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* change for to while. Input: scala source code; Output: scala source code */


public class Forchange {

    private static boolean ForDectect = true;

    private static ArrayList<String> reader(String inputFile) throws IOException {
        File file = new File(inputFile);
        ArrayList<String> list = new ArrayList<>();
        Pattern ForFind = Pattern.compile("for |for\\(");
        Matcher m;

        if (file.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String readline;
            while ((readline = br.readLine()) != null)
            {
                list.add(readline);
                m = ForFind.matcher(readline);
                if (m.find()) ForDectect = false;
            }
        }
        return list;
    }

    private static ArrayList<String> change(ArrayList<String> source){
        Pattern ForFind = Pattern.compile("for \\(|for\\(");
        Matcher m;
        ArrayList<String> After = new ArrayList<>();
        for (String line:source){
            m = ForFind.matcher(line);
            StringBuilder start= new StringBuilder();
            StringBuilder end= new StringBuilder();
            StringBuilder element = new StringBuilder();
            if (m.find()){
                int k = m.end();
                while (line.charAt(k)!='<') {
                    element.append(line.charAt(k));
                    k=k+1;
                }
                k=k+2;
                while (line.charAt(k)==' ') k=k+1;
                while (line.charAt(k)!=' ') {
                    start.append(line.charAt(k));
                    k=k+1;
                }
                System.out.println(line);
                while (line.charAt(k)!='o') k=k+1;
                k=k+1;
                while (line.charAt(k)==' ') k=k+1;
                int s=0;
                while (s>=0){
                    end.append(line.charAt(k));
                    k=k+1;
                    if (line.charAt(k)==')') s=s-1;
                    if (line.charAt(k)=='(') s=s+1;
                }
                After.add("var "+element+"="+start+"-1");
                After.add("while ("+element+"<"+end+"){");
                After.add(element+"="+element+"+1");
            }
            else After.add(line);
        }
        return After;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> SourceCode;
        String CodeName = "Property";
        String pathr = "customarray/src/edu/ucla/cs/bigfuzz/sparkprogram/";
        SourceCode = reader(pathr +CodeName+".scala");
        if (!ForDectect){
            SourceCode=change(SourceCode);
        }

        String AfterName = "Property1.scala";
        FileWriter codeFile = new FileWriter(pathr +AfterName);
        for (String line: SourceCode){
            codeFile.write(line+"\n");
        }
        codeFile.close();
    }
}
