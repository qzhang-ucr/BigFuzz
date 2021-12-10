package edu.ucla.cs.jqf.bigfuzz;

//import edu.berkeley.cs.jqf.fuzz.junit.GuidedFuzzing;
import edu.berkeley.cs.jqf.fuzz.junit.GuidedFuzzing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ProvFuzzDriver {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java " + ProvFuzzDriver.class + " TEST_CLASS TEST_METHOD [MAX_TRIALS]");
            System.exit(1);
        }

        String testClassName = args[0];
        String testMethodName = args[1];
        final int branch_id = Integer.parseInt(args[2]);
        Long maxTrials = args.length > 3 ? Long.parseLong(args[3]) : Long.MAX_VALUE;
        System.out.println("maxTrials: "+maxTrials);
        System.out.println("fuzz branch: "+branch_id);
//        File outputDirectory = new File("../fuzz-results");


        String file = "/home/ahmad/Documents/VT/project1/BigFuzz/dataset/ProvFuzz1/conf";
       try {
           long startTime = System.currentTimeMillis();

            String title = testClassName+"#"+testMethodName;
              Duration duration = Duration.of(100, ChronoUnit.SECONDS);
             //NoGuidance guidance = new NoGuidance(file, maxTrials, System.err);
             ProvFuzzGuidance guidance = new ProvFuzzGuidance("Test1", file, maxTrials, branch_id, duration, System.err);

             // Run the Junit test
            GuidedFuzzing.run(testClassName, testMethodName, guidance, System.out);

            if (Boolean.getBoolean("jqf.logCoverage")) {
                System.out.println(String.format("Covered %d edges.",
                        guidance.getCoverage().getNonZeroCount()));
            }


           long endTime = System.currentTimeMillis();
           System.out.println("*********Running Time:" + (endTime - startTime) + "ms");

       } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

    }
}
