package udfExtractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Configuration extends Logging  {
/*    static String JPF_HOME = "/Users/amytis/Projects/jpf/"; // Assuming that jpf/ contains jpf-core and jpf-symb
    static String HOME = ""; // Project Home to extract the class files of UDFs
    static String JUNIT_HOME = "/Users/amytis/Projects/jpf/"; // Junit Home folder
    static String JAD_EXE = "/Users/amytis/Projects/Test-Minimization-in-Big-Data/udf_extractor/jadmx158/jad";
    static String JAVA_RUN_DIR = "/Users/amytis/Projects/jpf/jpf-symbc/src/examples";
*/


    static String JPF_HOME = "/Users/malig/workspace/up_jpf/"; // Assuming that jpf/ contains jpf-core and jpf-symb
    static String HOME = ""; // Project Home to extract the class files of UDFs
    static String JUNIT_HOME = "/Users/malig/workspace/jpf/"; // Junit Home folder
    static String JAD_EXE = "/Users/malig/workspace/up_jpf/jadmx158/jad";
    static String JAVA_RUN_DIR = "/Users/malig/workspace/up_jpf/jpf-symbc/src/examples";
    static String Z3_LIB = "/Users/malig/workspace/up_jpf/z3/build/";
    static String PYTHON_PATH = "/Users/malig/workspace/up_jpf/z3/build/python";

    static String arr[] = "map,flatMap,filter,reduceByKey,reduce,reduceByKey".split(",");
    static ArrayList<String> spark_ops = new ArrayList<>(Arrays.asList(arr));
    static HashMap<String, String> map_args = new HashMap<>();
    static JPFDAGNode program_dag = null;
    static int K_BOUND = 2;
    

    //// TODO: 9/14/17 Populate the input arguments to each of the udfs
    static String JPF_FILE_PLACEHOLDER(String target, String fun_name, String example_build, boolean isString , int numInputs) {

        String input = "sym";
        for(int i = 1 ; i < numInputs ; i++){
            input +="#sym";
        }

        String str = "";
        if (isString) {
            str = "symbolic.strings=true\n" +
                    "symbolic.string_dp_timeout_ms=3000\n" +
                    "\n" +
                    "search.depth_limit = 10\n";
        }

        if (target.contains("reduce")) {
            return "target=" + target + "\n" +
                    "\n" +
                    "classpath=" + example_build + "\n" +
                    "#sourcepath=${jpf-symbc}/src/examples\n" +
                    "\n" +
                    "symbolic.method=" + target + "." + fun_name + "("+input+")\n" +
                    "\n" +
                    "symbolic.debug=true\n" +
                    "\n" +
                    "symbolic.dp=no_solver"
                    + "\n"
                    + "symbolic.lazy=true"
                    + "\n"
                    + "symbolic.arrays=true"
                    + "\n"
                    + "search.multiple_errors=true"
                    + "\n" + str +
                    "listener = gov.nasa.jpf.symbc.SymbolicListener\n" +
                    "#listener = gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener #for test-case generation" +
                    "\n";

        } else {


            return "target=" + target + "\n" +
                    "\n" +
                    "classpath=" + example_build + "\n" +
                    "#sourcepath=${jpf-symbc}/src/examples\n" +
                    "\n" +
                    "symbolic.method=" + target + "." + fun_name + "("+input+")\n" +
                    "\n" +
                    "\n" + "\n"
                    + "symbolic.lazy=true"
                    + "\n"
                    + "symbolic.arrays=true"
                    + "\n" +
                    "symbolic.debug=true\n" +
                    "\n" + str +
                    "listener = gov.nasa.jpf.symbc.SymbolicListener\n" +
                    "#listener = gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener #for test-case generation" +
                    "\n" +
                    "symbolic.dp=no_solver";

        }
    }

    static boolean isSparkDataflowOperator(String op) {
        return spark_ops.contains(op);
    }

    
    static HashMap<String, String[]> dag_map  = new HashMap<String, String[]>(); 
    public static void readSPFInputArgs(String classname){
        try(BufferedReader br = new BufferedReader(new FileReader(classname ))) {
            for(String line; (line = br.readLine()) != null; ) {
               String arr[] = line.split(">");
                if(arr.length < 2) {
                    logerr("Invalid Configuration File");
                    return;
                }else{
                	if(!arr[0].startsWith("\\")) {
                		if(arr[0].startsWith("DAG")) {
                			//DAG > reduceByKey4-map5:map5-join:join-filter2,map3:filter2-map1
                			String edges[]  = arr[1].split(":");
                			for(int i = edges.length -1 ; i>=0 ; i--) {
                				//reduce-map3
                				String[] parents = edges[i].split("-")[1].split(",");
                				dag_map.put(edges[i].split("-")[0] , parents);
                			}
                			program_dag = JPFDAGNode.generateJPFDAGNode(dag_map , edges[0].split("-")[0]);
                		//Read Bound values from the configuration file. 
                		}else if(arr[0].startsWith("K_BOUND")){ 
                			K_BOUND = Integer.parseInt(arr[1]); 
                		}else {
                			Configuration.map_args.put(arr[0].trim(), arr[1].trim());
                        logdebug("Adding1 input arguments : " + arr[0].trim() + " --> " + arr[1].trim());
                        			
                		}
                	}
              }
            }
            // line is not visible here.
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    static String getArgs(String op_name) {
        if (map_args.containsKey(op_name)) {
            return map_args.get(op_name);
        }
        return "";
    }

}