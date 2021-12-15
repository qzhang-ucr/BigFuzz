package udfExtractor;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;

import java.io.*;
import java.util.ArrayList;

public class UDFDecompilerAndExtractor extends Logging {

    String classfile = null;
    String classFile_jad = null;
    String outputJava = null;
    ArrayList<JPFDAGNode> jpf_dag = new ArrayList<>();


    public UDFDecompilerAndExtractor(String classf, String classf_jad, String output_java) {
        classfile = classf;
        classFile_jad = classf_jad;
        outputJava = output_java;
    }

public JPFDAGNode getDAG() {

	JPFDAGNode prev = null;
	for(JPFDAGNode node  : jpf_dag ) {
		if(prev ==null) {
			prev = node;
		}else {
			JPFDAGNode[] p = {prev};
			node.parents = p;
			prev = node;
		}
	}
	return prev;
}    
    
    public void parse(String str, String jpfdir) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        Document doc = new Document(str);
        parser.setSource(doc.get().toCharArray());

//        parser.setSource(str.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        SparkProgramVisitor spv = new SparkProgramVisitor(this , jpfdir , rewriter);
        for(IProblem problem : cu.getProblems()) {System.err.println(problem.getSourceLineNumber() + " > " + problem.getMessage());}
        System.out.println("___");
        cu.accept(spv);
//       TextEdit edits = null;
//        edits = spv.rewrite.rewriteAST(doc, null);
//        try{edits.apply(doc);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(doc.get());

    }
    //read file content into a string
    public String readFileToString(String filePath) throws IOException {
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
        return cleanJavaCode(fileData.toString());
    }

    public String cleanJavaCode(String s) {

        return s
                .replaceAll("String\\.\\.MODULE" ,"String\\.MODULE" )
                .replaceAll("BoxesRunTime\\.boxToInteger" , "");
    }

    //loop directory to get file list
    public void ParseFilesInDir( String jpfdir) throws Exception {

        //-----------------------------------------------------
        if (new File(classFile_jad).exists()) {
            loginfo(LogType.INFO, "Deleting file " + classFile_jad + " ...");
            new File(classFile_jad).delete();
        }
        String classFile_class = classfile + ".class";
        decompileProgram(classFile_class);
        //-----------------------------------------------------
        parse(readFileToString(classFile_jad), jpfdir);
    }


    public void decompileProgram(String file) {
        String[] args = new String[]{Configuration.JAD_EXE, file};
        runCommand(args);
//        String out = runCommand(args);
//        try {
//            FileWriter fw = new FileWriter(this.classFile_jad);
//            fw.write(out);
//            fw.close();
//        } catch (IOException e) {
//            System.err.println("Error writing jad file");
//        }
    }

    public String runCommand(String[] args) {
        StringBuilder stdout = new StringBuilder("");
        try {
            String s = "";
            for (String a : args) {
                s = s + "  " + a;
            }
            loginfo(LogType.INFO, "Running Command : " + s);
            Runtime runt = Runtime.getRuntime();
            Process p = runt.exec(s);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
            // read the output from the command

            while ((s = stdInput.readLine()) != null) {
                stdout.append(s);
                stdout.append("\n");
            }
            loginfo(LogType.INFO, stdout.toString());
            StringBuilder stderr = new StringBuilder("");
            while ((s = stdError.readLine()) != null) {
                stderr.append(s);
                stderr.append("\n");
            }
            loginfo(LogType.WARN, stderr.toString());
            stdError.close();
            stdInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stdout.toString();
    }
}
