package edu.ucla.cs.bigfuzz.customarray.applicable.MatrixMinMaxNN;

public class reduceByKey1 {
    public static void main(String[] args) {

    }
    static float applyReduce(float acc, float e) {
        return acc >= e ? e : acc;
    }
}
