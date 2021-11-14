package udfExtractor;

import org.eclipse.jdt.core.dom.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    //use ASTParse to parse string
    public static void parse(String str) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(str.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();


        cu.accept(new ASTVisitor() {
            Set names = new HashSet();

            public boolean visit(MethodDeclaration node) {
                SimpleName name = node.getName();
                this.names.add(name.getIdentifier());
                if (name.toString().contains("apply")) {
                    System.out.println("\n\n\n\n******* Printing Java UDF ********");
                    System.out.println(node.toString());
                    System.out.println("**********************************");

                }
                return true;
            }
        });
    }

    //read file content into a string
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
          //  System.out.println(numRead);
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    //loop directory to get file list
    public static void ParseFilesInDir() throws IOException {
        String classfile = "/home/ahmad/Documents/VT/project1/FineGrainedDataProvenance/target/scala-2.11/classes/main.scala.examples/benchmarks/input_reduction_benchmarks/MatrixMinMaxNN$"; //"/Users/malig/workspace/git/Test-Minimization-in-Big-Data/udf_extractor/target/scala-2.11/classes/WordCount$$anonfun$main$3";
        String classFile_jad = classfile.split("/")[classfile.split("/").length-1] + ".jad";
        if(new File(classFile_jad).exists()){
            new File(classFile_jad).delete();
        }
        String classFile_class = classfile + ".class";
        decompileProgram(classFile_class);
        parse(readFileToString(classFile_jad));
//        new File(classFile_jad).renameTo(new File(classfile.split("/")[classfile.split("/").length-1] + ".java"));
        // "/Users/malig/workspace/git/Test-Minimization-in-Big-Data/udf_extractor/target/scala-2.11/classes/WordCount$.jad"));
        // "/Users/malig/workspace/git/Test-Minimization-in-Big-Data/udf_extractor/src/main/java/Main.java"));
    }

    public static void main(String[] args) throws IOException {
        ParseFilesInDir();
        String classp = "/home/ahmad/Documents/VT/project1/FineGrainedDataProvenance/target/scala-2.11/classes/main.scala.examples/benchmarks/input_reduction_benchmarks/MatrixMinMaxNN$";
        String jadp = "/home/ahmad/Documents/VT/project1/BigFuzz/MatrixMinMaxNN$.jad";
        UDFDecompilerAndExtractor extractor = new UDFDecompilerAndExtractor(classp, jadp, "testing.java");
        try {
            extractor.ParseFilesInDir("test");
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public static void decompileProgram(String file) {
        String s = null;
        try {
            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            String jad_exe = "/home/ahmad/Documents/VT/project1/jad";
            Process p = Runtime.getRuntime().exec(jad_exe + " " + file);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            stdError.close();stdInput.close();
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}