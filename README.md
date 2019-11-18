# JQF + BigFuzz: Fuzz Testing for Big Data Analytics


cd to /direction/to/BigFuzz/customarray/target/classes

../../../bin/jqf-bigfuzz -c .:$(/direction/to/BigFuzz/scripts/classpath.sh) testdriver testmethod num/null

## TODO List
1. Spec implementation of GradeAnalysis and CommuteTpye  -> Jiayuan
2. Collect subjects for computation skew  -> Jiyuan
3. Add lower bounds for coverage-guided fuzzing  -> Qian
The problem is execution path in JDU coverage is different from instruction coverage in JQF
How to link the instruction coverage to different execution pathes?


