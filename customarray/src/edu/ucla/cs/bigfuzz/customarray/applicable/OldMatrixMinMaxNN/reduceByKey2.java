package edu.ucla.cs.bigfuzz.customarray.applicable.OldMatrixMinMaxNN;

public class reduceByKey2 {
    public static void main(String[] args) {

    }
    static float applyReduce(float acc, float e) {
        return acc <= e ? e : acc;
    }
}