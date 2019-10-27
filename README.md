# JQF + BigFuzz: Fuzz Testing for Big Data Analytics


## Overview
Coverage for dataflow operators


## How to use
cd to /director/to/BigFuzz/BigFuzzTest/target/classes

../../../bin/jqf-bigfuzz -c .:$(/director/to/BigFuzz/scripts/classpath.sh) WordCountDriver testWordCount 3

## TODO List
1. implemente Java version of SalaryAnalysis and JoinSkew
2. put all generated input files to BigFuzz/dataset/
3. write mutation for SalaryAnalysis
3.1 random generate (I already have a data generator in /BigFuzz/fuzz/bigfuzzpackage/datagen/)
3.2 random change values in each field. e.g., [90024, 45,5000] -> [90025, 45, 5000]
3.3 random change int to float. e.g., [90024, 45, 5000] -> [90024, 45.0, 5000] / [90024, 45, 5000.0]
3.4 random insert letters e.g., [90024, 50, 5000] -> [90024a, 50, 5000] / [90024, 50, a5000]
3.4 random delete / add one column

lower bound of total coverage


