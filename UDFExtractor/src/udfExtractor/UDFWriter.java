package udfExtractor;

import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


public class UDFWriter {
    String filename = null;
    BufferedWriter bw = null;
    HashMap<String, FunctionStructure> functions = new HashMap<>();
    String argsToMain = null;
    boolean isString = false;


    public UDFWriter(String filen, String argsmain) {
        argsToMain = argsmain;
        try {

            filename = filen.replace("$", "");
            String arr[] = filename.split("/");
            String file_name = arr[arr.length - 1];
            File file = new File(filename);
            filename = file_name;
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            // bw.write(skeleton);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enrollFunction(String name, FunctionStructure code) {
        functions.put(name, code);
    }

    public String getInputParamters(String f_name) {
        FunctionStructure fs = functions.get(f_name);
        String s = "";
        for (Object par : fs.parameters) {
            SingleVariableDeclaration p = (SingleVariableDeclaration) par;
            s = s + par + ",";
        }
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    public String getInputParamtersName(String f_name) {
        FunctionStructure fs = functions.get(f_name);
        String s = "";
        for (Object par : fs.parameters) {
            SingleVariableDeclaration p = (SingleVariableDeclaration) par;
            s = s + ((SingleVariableDeclaration) par).getName().getIdentifier() + ",";
        }
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    public String getReturnType(String f_name) {
        FunctionStructure fs = functions.get(f_name);

        return fs.returnType.toString().equals("ArrayOps")?"Object[]":fs.returnType.toString();
    }

    public void write(Set<String> used_func, String target_func, SparkProgramVisitor visitor) {
        String wrapper_func_body = "";
        String wrapper_name = "applyReduce";
        isString = argsToMain.startsWith("\"") && argsToMain.endsWith("\"");
        String method_call = target_func + "(" + argsToMain + ");\n";
        if (filename.startsWith("reduce")) {
            wrapper_name = "applyReduce";
            wrapper_func_body = "static int " + wrapper_name + "( int[] a) {\n" +
                    "   int s = a[0];\n" +
                    "   for(int i = 1 ; i < " + Runner.loop_bound() + " ; i++){\n" + //// This is where we set the upper bound for the loop in reduce.
                    "       s = " + target_func + "( s , a[i] );\n" +
                    "   }\n" +
                    "   return s;\n" +
                    "}\n";
            visitor.setTargetJPF(wrapper_name);
            target_func = wrapper_name;
            method_call = "int[] arr = " + argsToMain + ";\n       " + target_func + "(arr);\n";
        }
        /*else if (filename.startsWith("flatMap")) {
            wrapper_name = "applyFlatmap";
            String retType = getReturnType(target_func);
            if(retType.endsWith("[]")) retType = retType.replace("[]", "");
            wrapper_func_body = "static " + retType +" "+  wrapper_name + "( "+ getInputParamters(target_func)+" ) {\n" +
                    "   return " +target_func+"(" + getInputParamtersName(target_func) +")["+ Runner.loop_bound + "];\n"+
                    "}\n";
            visitor.setTargetJPF(wrapper_name);
            target_func = wrapper_name;
            method_call =  target_func + "("+argsToMain+");\n";
        }*/

        String skeleton =
                "public class " + filename.replace(".java", "") + " { \n" +
                        "   public static void main(String[] args) { \n" +
                        "       " + method_call +
                        "   }\n ";

        String content = skeleton;
        content = content + wrapper_func_body;

        for (String fun : used_func) {
            if (functions.containsKey(fun)) {
                String fun_code = getFunctionCode(fun);
                if (fun_code.contains("Tuple2")) {
                    fun_code = fun_code.replaceAll("Tuple2", filename.replace(".java", ""));
                    content += fun_code;
                    content += replaceTuple2(filename.replace(".java", ""));

                } else
                    content += getFunctionCode(fun);

            }

        }

        try {
            bw.write(content);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

        }
    }

    public void close() {
        try {
            if (bw != null)
                bw.write("}");
            bw.close();
        } catch (Exception ex) {
            System.out.println("Error in closing the BufferedWriter" + ex);
        }
    }

    public String getReturnInstant(String s) {
        switch (s) {
            case "int":
                return "0";

            case "String":
                return "\"a\"";
            case "boolean":
                return "false";
            default:
                return "null";

        }
    }

    String wrapNullAroundBody(String body, String returnT, String Varname, int wrapType) {
        String retVa = getReturnInstant(returnT);
        switch (wrapType) {
            case 1:
                return " { \n if( " + Varname + " == null ){ \n"
                        + "return " + retVa + ";"
                        + "\n"
                        + "}else\n"
                        + body + "\n}";
            case 2:
                return " { \n"
                        + "     if( " + Varname + " == null ){ \n"
                        + "			return " + retVa + ";\n"
                        + "     }else if( " + Varname + "._2 == null ){ \n"
                        + "		    return " + retVa + ";\n"
                        + "     }else\n"
                        + body + "\n }";
            default:
                return body;
        }

    }

    int wrapNull = 0;
    int symInputs = 0;

    public String getFunctionCode(String name) {
        FunctionStructure fs = functions.get(name);
        String s = "";
        for (Modifier m : fs.mods) {
            s = s + " " + m.getKeyword().toString();
        }

        if(fs.returnType.toString().equals("ArrayOps")){
            s = s + " " + "Object[]";
        }else {
            s = s + " " + fs.returnType.toString();
        }
        s = s + " " + name + "(";
        if (name.equals("apply")) {
            System.out.println("");
        }


        for (Object par : fs.parameters) {
            SingleVariableDeclaration p = (SingleVariableDeclaration) par;
            String para = getParameterType(p, fs);

            s = s + para + ",";
        }
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        s = s + ")"; //+ fs.body.toString();
        String body_str = fs.body.toString();
//        if (wrapNull > 0) {
//            body_str = wrapNullAroundBody(body_str, fs.returnType.toString(), ((SingleVariableDeclaration) fs.parameters.get(0)).getName().getIdentifier(), wrapNull);
//        }


        for (String ty : fs.map.keySet()) {
            String typ = fs.map.get(ty);
            body_str = body_str.replaceAll("\\Q(" + typ + ")\\E", "");
            body_str = body_str.replaceAll("\\QPredef$.MODULE$.refArrayOps\\E" , "");
            body_str = body_str.replaceAll("ArrayOps" , "Object[]");
        }
        body_str = tupleRenaming(body_str);
        return s + body_str;
    }

    HashMap<String, String> replacements = new HashMap<>();

    private String tupleRenaming(String code) {
        for (String k : replacements.keySet()) {
            code = code.replaceAll(k, replacements.get(k));
        }
        return code;
    }

    private String getParameterType(SingleVariableDeclaration p, FunctionStructure fs) {

        String par_name = p.getName().getIdentifier();
        if (p.getType().toString().equals("Tuple2")) {
            String _1 = null;
            String _2 = null;
            for (String s : fs.map.keySet()) {
                String[] s_arr = s.split("\\.");
                if (par_name.equals(s_arr[0])) {
                    if (s_arr.length > 1) {
                        if (s_arr[1].startsWith("_1")) {
                            _1 = fs.map.get(s);
                        } else if (s_arr[1].startsWith("_2")) {
                            _2 = fs.map.get(s);
                        }
                    }
                }
            }

            if (_1 == null && _2 == null) {
                replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                replacements.put(par_name + "._2\\(\\)", par_name + "_t2");
                replacements.put(par_name + "._1", par_name + "_t1");
                replacements.put(par_name + "._2", par_name + "_t2");
                wrapNull = 1;
                symInputs = 2;
                return "int " + par_name + "_t1" + ", int " + par_name + "_t2";
            } else if (_1 == null) {
                if (_2.equals("String")) {
                    isString = true;
                    replacements.put(par_name + "._1", par_name + "_t1");
                    replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                    replacements.put(par_name + "._2", par_name + "_t2");
                    replacements.put(par_name + "._2\\(\\)", par_name + "_t2");
                    symInputs = 2;
                    wrapNull = 1;
                    return "int " + par_name + "_t1" + ", String " + par_name + "_t2";
                } else if (_2.equals("Tuple2")) {
                    isString = true;
                    replacements.put(par_name + "._1", par_name + "_t1");
                    replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                    replacements.put("\\(" + par_name + "._2\\(\\)\\)._1\\(\\)", par_name + "_t2");
                    replacements.put("\\(" + par_name + "._2\\(\\)\\)._2\\(\\)", par_name + "_t3");
                    wrapNull = 2;
                    symInputs = 3;
                    return "String " + par_name + "_t1" + ", int " + par_name + "_t2" + ", String " + p.getName().getIdentifier() + "_t3";
                }
            } else if (_2 == null) {
                if (_1.equals("String")) {
                    isString = true;
                    replacements.put(par_name + "._1", par_name + "_t1");
                    replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                    replacements.put(par_name + "._2", par_name + "_t2");
                    replacements.put(par_name + "._2\\(\\)", par_name + "_t2");
                    wrapNull = 1;
                    symInputs = 2;
                    return "String " + par_name + "_t1" + ", int " + par_name + "_t2";
                }
            } else {
                if (_1.equals("String") && _2.equals("String")) {
                    isString = true;
                    replacements.put(par_name + "._1", par_name + "_t1");
                    replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                    replacements.put(par_name + "._2", par_name + "_t2");
                    replacements.put(par_name + "._2\\(\\)", par_name + "_t2");
                    wrapNull = 1;
                    symInputs = 2;
                    return "String " + par_name + "_t1" + ", String " + par_name + "_t2";
                } else if (_1.equals("String") && _2.equals("Tuple2")) {
                    isString = true;
                    replacements.put(par_name + "._1", par_name + "_t1");
                    replacements.put(par_name + "._1\\(\\)", par_name + "_t1");
                    replacements.put("\\(" + par_name + "._2\\(\\)\\)._1\\(\\)", par_name + "_t2");
                    replacements.put("\\(" + par_name + "._2\\(\\)\\)._2\\(\\)", par_name + "_t3");
                    wrapNull = 2;
                    symInputs = 3;
                    return "String " + par_name + "_t1" + ", int " + par_name + "_t2" + ", String " + par_name + "_t3";
                }
            }
            wrapNull = 1;
            symInputs = 2;
            return "int " + par_name + "_t1" + ", int " + par_name + "_t2";
        } else {
            return p.toString();
        }
    }
    

    String replaceTuple2(String s) {
        return "String sa,sb;\n" +
                "\nint ia,ib;\n" +
                "public int _1(){\n" +
                "	return ia;\n" +
                "}\n" +
                "public int _2(){\n" +
                "	return ib;\n" +
                "}\n" +
                "public " + s + "(int k, int v){\n" +
                "        ia = k;\n" +
                "        ib = v;\n" +
                "}\n" +
                "public " + s + "(String k, int v){\n" +
                "        sa = k;\n" +
                "        ib = v;\n" +
                "}\n" +
                "public " + s + "(int k, String v){\n" +
                "        ia = k;\n" +
                "        sb = v;\n" +
                "}\n" +
                "public " + s + "(String k, String v){\n" +
                "        sa = k;\n" +
                "        sb = v;\n" +
                "}";
    }
}
