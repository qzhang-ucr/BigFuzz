package udfExtractor;

import java.util.HashMap;

public class JPFDAGNode{
     String operator_name ;
     String jpf_file;
     JPFDAGNode[] parents = new JPFDAGNode[0];

     public JPFDAGNode(String op, String file){
         operator_name  = op;
         jpf_file = file;
     }
     
     public JPFDAGNode(String op, String file , JPFDAGNode[] par){
         operator_name  = op;
         jpf_file = file;
         parents =  par;
     }

     public String getOperatorName() {
        return operator_name;
     }

     public String getJPFFileName() {
        return jpf_file;
     }
     

     public JPFDAGNode[] getParents() {
        return parents;
     }
     
     public static JPFDAGNode generateJPFDAGNode(HashMap<String, String[]> map , String node) {
    	 	if(map.containsKey(node)) {
        	 	String[] parents = map.get(node);
        	 	JPFDAGNode nodes[] = new JPFDAGNode[parents.length];
        	 	int i = 0;
        	 	for(String parent : parents) {
        	 		nodes[i] = generateJPFDAGNode(map , parent);
        	 		i++;
        	 	}
        	 return new JPFDAGNode(node , node, nodes);       	 			
    	 	}
    	 	else {
    	 		return new JPFDAGNode(node , node); 
    	 	}

     }
 }