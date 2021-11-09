package edu.ucla.cs.bigfuzz.customarray.applicable.MatrixMinMaxNN;

import java.util.ArrayList;
import java.util.Arrays;

public class flatMap1 {
    public static void main(String[] args) {
        System.out.println(apply("a,b,c"));
    }
    static final String[] apply(String r){
        ArrayList<String> arr = new ArrayList<String> (Arrays.asList(r.split(",")));
        return arr.subList(1, arr.size()).toArray(new String[0]);
    }
}