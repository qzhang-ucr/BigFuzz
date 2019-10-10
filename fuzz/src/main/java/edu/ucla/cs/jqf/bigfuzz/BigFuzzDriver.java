package edu.ucla.cs.jqf.bigfuzz;

import edu.berkeley.cs.jqf.fuzz.junit.GuidedFuzzing;
import edu.berkeley.cs.jqf.fuzz.random.NoGuidance;

import java.io.File;

public class BigFuzzDriver {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java " + BigFuzzDriver.class + " TEST_CLASS TEST_METHOD [OUTPUT_DIR [SEEDS...]]");
            System.exit(1);
        }

        String testClassName = args[0];
        String testMethodName = args[1];
        Long maxTrials = args.length > 2 ? Long.parseLong(args[2]) : Long.MAX_VALUE;
        File outputDirectory = new File("/home/qzhang/Programs/BigFuzz/fuzz-results");
 //       String outputDirectoryName = args.length > 2 ? args[2] : "fuzz-results";
//        File outputDirectory = new File(outputDirectoryName);
        try {
            String title = testClassName+"#"+testMethodName;
            // Load the guidance
            BigFuzzGuidance guidance = new BigFuzzGuidance(title, maxTrials, null, outputDirectory, System.err);

            // Run the Junit test
            GuidedFuzzing.run(testClassName, testMethodName, guidance, System.out);

            if (Boolean.getBoolean("jqf.logCoverage")) {
                System.out.println(String.format("Covered %d edges.",
                        guidance.getCoverage().getNonZeroCount()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
