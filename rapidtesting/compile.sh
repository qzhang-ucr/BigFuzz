#!/bin/bash

cd generated

javac -cp \
        ../../fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz:$(../../scripts/classpath.sh) \
        ../../fuzz/src/main/java/edu/ucla/cs/jqf/bigfuzz/{{BigFuzzMutation,BigFuzzGuidance,BigFuzzDriver}.java,InputReductionMutation.java} \
         -d ../../fuzz/target/classes

../../bin/jqf-bigfuzz \
        -c ../../customarray/target/classes:$(../../scripts/classpath.sh)  \
        MatrixMinMaxNNDriver testMatrixMinMaxNN 10