# JQF + BigFuzz: Fuzz Testing for Big Data Analytics


# How to run the tool
- Download and Build
- Prepare data-specific mutation that implements BigFuzzMutation (will be bounded by data format/type)
- Change line 96 in BigFuzzGuidance to the specific mutation
- Update your test driver to make your program read a config file that contains the paths to all your data files
- Default seed is ./dataset/config in line 25 BigFuzzDriver.java

cd to /direction/to/BigFuzz/customarray/target/classes

../../../bin/jqf-bigfuzz -c .:$(/direction/to/BigFuzz/scripts/classpath.sh) testdriver testmethod num/null



