package edu.ucla.cs.jqf.bigfuzz;

import edu.berkeley.cs.jqf.fuzz.guidance.Guidance;
import edu.berkeley.cs.jqf.fuzz.guidance.GuidanceException;
import edu.berkeley.cs.jqf.fuzz.guidance.Result;
import edu.berkeley.cs.jqf.fuzz.guidance.TimeoutException;
import edu.berkeley.cs.jqf.fuzz.util.Coverage;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;

import java.io.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.lang.Math.ceil;
import static java.lang.Math.log;

/**
 * A guidance that performs coverage-guided fuzzing using JDU (Joint Dataflow and UDF)
 * Mutations: 1. randomly mutation
 * code coverage guidance: control flow coverage, dataflow operators's coverage
 */
public class BigFuzzSalaryGuidance implements Guidance {

    /** A pseudo-random number generator for generating fresh values. */
    private Random random = new Random();

    /** The name of the test for display purposes. */
    protected final String testName;

    private boolean keepGoing = true;
    private static boolean KEEP_GOING_ON_ERROR = true;
    private Coverage coverage;

    /** The max amount of time to run for, in milli-seconds */
    protected final long maxDurationMillis;

    /** The number of trials completed. */
    private long numTrials = 0;

    /** The number of valid inputs. */
    protected long numValid = 0;

    /** The directory where fuzzing results are written. */
    protected final File outputDirectory;

    /** The directory where saved inputs are written. */
    protected File savedInputsDirectory;

    /** The directory where saved inputs are written. */
    protected File savedFailuresDirectory;


    private final long maxTrials;
    private final PrintStream out;
    private long numDiscards = 0;
    private final float maxDiscardRatio = 0.9f;

    /** Index of currentInput in the savedInputs -- valid after seeds are processed (OK if this is inaccurate). */
    protected int currentParentInputIdx = 0;


    protected Deque<Input> seedInputs = new ArrayDeque<>();

    /** Current input that's running -- valid after getInput() and before handleResult(). */
    protected Input<?> currentInput;

    /** Set of saved inputs to fuzz. */
    protected ArrayList<Input> savedInputs = new ArrayList<>();

    /** Blind fuzzing -- if true then the queue is always empty. */
    protected boolean blind;

    /** Validity fuzzing -- if true then save valid inputs that increase valid coverage */
    protected boolean validityFuzzing;

    /** Number of saved inputs.
     *
     * This is usually the same as savedInputs.size(),
     * but we do not really save inputs in TOTALLY_RANDOM mode.
     */
    protected int numSavedInputs = 0;

    /** Coverage statistics for a single run. */
    protected Coverage runCoverage = new Coverage();

    /** Cumulative coverage statistics. */
    protected Coverage totalCoverage = new Coverage();

    /** Cumulative coverage for valid inputs. */
    protected Coverage validCoverage = new Coverage();

    /** A mapping of coverage keys to inputs that are responsible for them. */
    protected Map<Object, Input> responsibleInputs = new HashMap<>(totalCoverage.size());

    /** The maximum number of keys covered by any single input found so far. */
    protected int maxCoverage = 0;

    /** The set of unique failures found so far. */
    protected Set<List<StackTraceElement>> uniqueFailures = new HashSet<>();

    // ---------- LOGGING / STATS OUTPUT ------------

    /** Whether to print log statements to stderr (debug option; manually edit). */
    protected final boolean verbose = true;

    /** A system console, which is non-null only if STDOUT is a console. */
    protected final Console console = System.console();

    /** Time since this guidance instance was created. */
    protected final Date startTime = new Date();

    /** Time at last stats refresh. */
    protected Date lastRefreshTime = startTime;

    /** Total execs at last stats refresh. */
    protected long lastNumTrials = 0;

    /** Minimum amount of time (in millis) between two stats refreshes. */
    protected static final long STATS_REFRESH_TIME_PERIOD = 300;

    /** The file where log data is written. */
    protected File logFile;

    /** The file where saved plot data is written. */
    protected File statsFile;

    /** The currently executing input (for debugging purposes). */
    protected File currentInputFile;

    // ------------- TIMEOUT HANDLING ------------

    /** Timeout for an individual run. */
    protected long singleRunTimeoutMillis;

    /** Date when last run was started. */
    protected Date runStart;

    /** Number of conditional jumps since last run was started. */
    protected long branchCount;

    // ------------- FUZZING HEURISTICS ------------

    /** Whether to save only valid inputs **/
    static final boolean SAVE_ONLY_VALID = Boolean.getBoolean("jqf.ei.SAVE_ONLY_VALID");

    /** Max input size to generate. */
    static final int MAX_INPUT_SIZE = Integer.getInteger("jqf.ei.MAX_INPUT_SIZE", 10240);

    /** Whether to generate EOFs when we run out of bytes in the input, instead of randomly generating new bytes. **/
    static final boolean GENERATE_EOF_WHEN_OUT = Boolean.getBoolean("jqf.ei.GENERATE_EOF_WHEN_OUT");

    /** Mean number of mutations to perform in each round. */
    static final double MEAN_MUTATION_COUNT = 8.0;

    /** Mean number of contiguous bytes to mutate in each mutation. */
    static final double MEAN_MUTATION_SIZE = 4.0; // Bytes

    /** Whether to save inputs that only add new coverage bits (but no new responsibilities). */
    static final boolean SAVE_NEW_COUNTS = true;

    /** Whether to steal responsibility from old inputs (this increases computation cost). */
    static final boolean STEAL_RESPONSIBILITY = Boolean.getBoolean("jqf.ei.STEAL_RESPONSIBILITY");

    /** Baseline number of mutated children to produce from a given parent input. */
    static final int NUM_CHILDREN_BASELINE = 50;

    /** Multiplication factor for number of children to produce for favored inputs. */
    static final int NUM_CHILDREN_MULTIPLIER_FAVORED = 20;

    /** Number of mutated inputs generated from currentInput. */
    protected int numChildrenGeneratedForCurrentParentInput = 0;

    /** Number of cycles completed (i.e. how many times we've reset currentParentInputIdx to 0. */
    protected int cyclesCompleted = 0;

    /** Number of favored inputs in the last cycle. */
    protected int numFavoredLastCycle = 0;

    public static abstract class Input<K> implements Iterable<Integer> {

        /**
         * The file where this input is saved.
         *
         * <p>This field is null for inputs that are not saved.</p>
         */
        File saveFile = null;

        /**
         * An ID for a saved input.
         *
         * <p>This field is -1 for inputs that are not saved.</p>
         */
        int id;

        /**
         * The description for this input.
         *
         * <p>This field is modified by the construction and mutation
         * operations.</p>
         */
        String desc;

        /**
         * The run coverage for this input, if the input is saved.
         *
         * <p>This field is null for inputs that are not saved.</p>
         */
        Coverage coverage = null;

        /**
         * The number of non-zero elements in `coverage`.
         *
         * <p>This field is -1 for inputs that are not saved.</p>
         *
         * <p></p>When this field is non-negative, the information is
         * redundant (can be computed using {@link Coverage#getNonZeroCount()}),
         * but we store it here for performance reasons.</p>
         */
        int nonZeroCoverage = -1;

        /**
         * The number of mutant children spawned from this input that
         * were saved.
         *
         * <p>This field is -1 for inputs that are not saved.</p>
         */
        int offspring = -1;

        /**
         * Whether this input resulted in a valid run.
         */
        boolean valid = false;

        /**
         * The set of coverage keys for which this input is
         * responsible.
         *
         * <p>This field is null for inputs that are not saved.</p>
         *
         * <p>Each coverage key appears in the responsibility set
         * of exactly one saved input, and all covered keys appear
         * in at least some responsibility set. Hence, this list
         * needs to be kept in-sync with {@link #responsibleInputs}.</p>
         */
        Set<Object> responsibilities = null;


        /**
         * Create an empty input.
         */
        public Input() {
            desc = "random";
        }

        /**
         * Create a copy of an existing input.
         *
         * @param toClone the input map to clone
         */
        public Input(Input toClone) {
            desc = String.format("src:%06d", toClone.id);
        }

        public abstract int getOrGenerateFresh(K key, Random random);
        public abstract int size();
        public abstract Input fuzz(Random random);
        public abstract void gc();



        /**
         * Returns whether this input should be favored for fuzzing.
         *
         * <p>An input is favored if it is responsible for covering
         * at least one branch.</p>
         *
         * @return
         */
        public boolean isFavored() {
            return responsibilities.size() > 0;
        }


        /**
         * Sample from a geometric distribution with given mean.
         *
         * Utility method used in implementing mutation operations.
         *
         * @param random a pseudo-random number generator
         * @param mean the mean of the distribution
         * @return a randomly sampled value
         */
        public static int sampleGeometric(Random random, double mean) {
            double p = 1 / mean;
            double uniform = random.nextDouble();
            return (int) ceil(log(1 - uniform) / log(1 - p));
        }
    }

    /** Spawns a new input from thin air (i.e., actually random) */
    protected Input<?> createFreshInput() {
        return new LinearInput();
    }

    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }
    public BigFuzzSalaryGuidance(String testName, long maxTrials, Duration duration, File outputDirectory, PrintStream out) throws IOException {

        this.testName = testName;
        this.maxDurationMillis = duration != null ? duration.toMillis() : Long.MAX_VALUE;
        this.outputDirectory = outputDirectory;

        if (maxTrials <= 0) {
            throw new IllegalArgumentException("maxTrials must be greater than 0");
        }
        this.maxTrials = maxTrials;
        this.out = out;
        prepareOutputDirectory();
    }
    private void prepareOutputDirectory() throws IOException {

        // Create the output directory if it does not exist
        if (!outputDirectory.exists()) {
            if (!outputDirectory.mkdirs()) {
                throw new IOException("Could not create output directory" +
                        outputDirectory.getAbsolutePath());
            }
        }

        // Make sure we can write to output directory
        if (!outputDirectory.isDirectory() || !outputDirectory.canWrite()) {
            throw new IOException("Output directory is not a writable directory: " +
                    outputDirectory.getAbsolutePath());
        }

        // Name files and directories after AFL
        this.savedInputsDirectory = new File(outputDirectory, "corpus");
        this.savedInputsDirectory.mkdirs();
        this.savedFailuresDirectory = new File(outputDirectory, "failures");
        this.savedFailuresDirectory.mkdirs();
        this.statsFile = new File(outputDirectory, "plot_data");
        this.logFile = new File(outputDirectory, "fuzz.log");
        this.currentInputFile = new File(outputDirectory, ".cur_input");


        // Delete everything that we may have created in a previous run.
        // Trying to stay away from recursive delete of parent output directory in case there was a
        // typo and that was not a directory we wanted to nuke.
        // We also do not check if the deletes are actually successful.
        statsFile.delete();
        logFile.delete();
        for (File file : savedInputsDirectory.listFiles()) {
            file.delete();
        }
        for (File file : savedFailuresDirectory.listFiles()) {
            file.delete();
        }

        appendLineToFile(statsFile,"# unix_time, cycles_done, cur_path, paths_total, pending_total, " +
                "pending_favs, map_size, unique_crashes, unique_hangs, max_depth, execs_per_sec, valid_inputs, invalid_inputs, valid_cov");


    }

    private int getnt(Input parentInput) {
        // Baseline is a constant
        int target = 0;
        /*int target = NUM_CHILDREN_BASELINE;

        // We like inputs that cover many things, so scale with fraction of max
        if (maxCoverage > 0) {
            target = (NUM_CHILDREN_BASELINE * parentInput.nonZeroCoverage) / maxCoverage;
        }

        // We absolutely love favored inputs, so fuzz them more
        if (parentInput.isFavored()) {
            target = target * NUM_CHILDREN_MULTIPLIER_FAVORED;
        }*/

        return target;
    }

    private int getTargetChildrenForParent(Input parentInput) {
        // Baseline is a constant
        int target = NUM_CHILDREN_BASELINE;

        // We like inputs that cover many things, so scale with fraction of max
        if (maxCoverage > 0) {
            target = (NUM_CHILDREN_BASELINE * parentInput.nonZeroCoverage) / maxCoverage;
        }

        // We absolutely love favored inputs, so fuzz them more
        if (parentInput.isFavored()) {
            target = target * NUM_CHILDREN_MULTIPLIER_FAVORED;
        }

        return target;
    }

    @Override
    public InputStream getInput()
    {
        System.out.println("xxxBigFuzzSalaryGuidance::getInputxxx");
        //return Guidance.createInputStream(() -> random.nextInt(256));
        // Clear coverage stats for this run
        runCoverage.clear();

        // Choose an input to execute based on state of queues
        if (!seedInputs.isEmpty()) {
            // First, if we have some specific seeds, use those
            currentInput = seedInputs.removeFirst();

            // Hopefully, the seeds will lead to new coverage and be added to saved inputs

        } else if (savedInputs.isEmpty()) {
            // If no seeds given try to start with something random
            if (!blind && numTrials > 100_000) {
                throw new GuidanceException("Too many trials without coverage; " +
                        "likely all assumption violations");
            }

            // Make fresh input using either list or maps
            // infoLog("Spawning new input from thin air");
            currentInput = createFreshInput();
            System.out.println("parent input 1 ");
        } else {
            // The number of children to produce is determined by how much of the coverage
            // pool this parent input hits
            Input currentParentInput = savedInputs.get(currentParentInputIdx);
            System.out.println("parent input 2");
            int targetNumChildren = getTargetChildrenForParent(currentParentInput);
            if (numChildrenGeneratedForCurrentParentInput >= targetNumChildren) {
                // Select the next saved input to fuzz
                currentParentInputIdx = (currentParentInputIdx + 1) % savedInputs.size();

                // Count cycles
                if (currentParentInputIdx == 0) {
                    completeCycle();
                }

                numChildrenGeneratedForCurrentParentInput = 0;
            }
            Input parent = savedInputs.get(currentParentInputIdx);

            // Fuzz it to get a new input
            // infoLog("Mutating input: %s", parent.desc);
            currentInput = parent.fuzz(random);
            numChildrenGeneratedForCurrentParentInput++;

            // Write it to disk for debugging
            try {
                writeCurrentInputToFile(currentInputFile);
            } catch (IOException ignore) { }

            // Start time-counting for timeout handling
            this.runStart = new Date();
            this.branchCount = 0;
        }

        return createParameterStream();
    }
    protected void writeCurrentInputToFile(File saveFile) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile))) {
            for (Integer b : currentInput) {
                assert (b >= 0 && b < 256);
                out.write(b);
            }
        }

    }
    protected InputStream createParameterStream() {
        // Return an input stream that reads bytes from a linear array
        return new InputStream() {
            int bytesRead = 0;
            String initialString = "text1111111";
            byte[] bytes = initialString.getBytes();
            @Override
            public int read() throws IOException {
                assert currentInput instanceof LinearInput : "ZestGuidance should only mutate LinearInput(s)";

                // For linear inputs, get with key = bytesRead (which is then incremented)
                LinearInput linearInput = (LinearInput) currentInput;
                // Attempt to get a value from the list, or else generate a random value
                /*if(bytesRead>=bytes.length)
                {
                    return -1;
                }
                //int ret = bytes[bytesRead++];*/
                int ret = linearInput.getOrGenerateFresh(bytesRead++, random);
                //bytesRead++;
                //System.out.println("read("+bytesRead+") = "+ret);
                return ret;
            }
        };
    }

    /** Writes a line of text to a given log file. */
    protected void appendLineToFile(File file, String line) throws GuidanceException {
        //System.out.println("appendLineToFile: ");
        //System.out.println("appendLineToFile: "+line);
        /*if(file == null)
        {
            System.out.println("File is NULL");
        }*/
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            out.println(line);
        } catch (IOException e) {
            //System.out.println("appendLineToFile:throw: "+e.getMessage());
            throw new GuidanceException(e);
        }

    }

    /** Writes a line of text to the log file. */
    protected void infoLog(String str, Object... args) {
        if (verbose) {
            String line = String.format(str, args);
            if (logFile != null) {
                appendLineToFile(logFile, line);

            } else {
                System.err.println(line);
            }
        }
    }

    /** Handles the end of fuzzing cycle (i.e., having gone through the entire queue) */
    protected void completeCycle() {
        // Increment cycle count
        cyclesCompleted++;
        infoLog("\n# Cycle " + cyclesCompleted + " completed.");

        // Go over all inputs and do a sanity check (plus log)
        infoLog("Here is a list of favored inputs:");
        int sumResponsibilities = 0;
        numFavoredLastCycle = 0;
        for (Input input : savedInputs) {
            if (input.isFavored()) {
                int responsibleFor = input.responsibilities.size();
                infoLog("Input %d is responsible for %d branches", input.id, responsibleFor);
                sumResponsibilities += responsibleFor;
                numFavoredLastCycle++;
            }
        }
        int totalCoverageCount = totalCoverage.getNonZeroCount();
        infoLog("Total %d branches covered", totalCoverageCount);
        if (sumResponsibilities != totalCoverageCount) {
            throw new AssertionError("Responsibilty mistmatch");
        }

        // Break log after cycle
        infoLog("\n\n\n");
    }


    @Override
    public boolean hasInput() {
        return keepGoing;
    }

    @Override
    public void handleResult(Result result, Throwable error) {

        // Stop timeout handling
        this.runStart = null;

        System.out.println("BigFuzzGuidance::handleResult");
        this.numTrials++;

        boolean valid = result == Result.SUCCESS;

        if (valid) {
            // Increment valid counter
            this.numValid++;
        }

        // Display error stack trace in case of failure
        if (result == Result.FAILURE) {
            if (out != null) {
                error.printStackTrace(out);
            }
            this.keepGoing = KEEP_GOING_ON_ERROR;
        }

        // Keep track of discards
        if (result == Result.INVALID) {
            numDiscards++;
        }

        // Stopping criteria
        if (numTrials >= maxTrials) {
            this.keepGoing = false;
        }

        if (numTrials > 10 && ((float) numDiscards)/((float) (numTrials)) > maxDiscardRatio) {
            throw new GuidanceException("Assumption is too strong; too many inputs discarded");
        }

        if (result == Result.SUCCESS || result == Result.INVALID) {

            // Coverage before
            int nonZeroBefore = totalCoverage.getNonZeroCount();
            int validNonZeroBefore = validCoverage.getNonZeroCount();

            // Compute a list of keys for which this input can assume responsiblity.
            // Newly covered branches are always included.
            // Existing branches *may* be included, depending on the heuristics used.
            // A valid input will steal responsibility from invalid inputs
            Set<Object> responsibilities = computeResponsibilities(valid);

            // Update total coverage
            boolean coverageBitsUpdated = totalCoverage.updateBits(runCoverage);
            if (valid) {
                validCoverage.updateBits(runCoverage);
            }

            // Coverage after
            int nonZeroAfter = totalCoverage.getNonZeroCount();
            if (nonZeroAfter > maxCoverage) {
                maxCoverage = nonZeroAfter;
            }
            int validNonZeroAfter = validCoverage.getNonZeroCount();

            // Possibly save input
            boolean toSave = true;
            String why = "";


            if (SAVE_NEW_COUNTS && coverageBitsUpdated) {
                toSave = true;
                why = why + "+count";
            }

            // Save if new total coverage found
            if (nonZeroAfter > nonZeroBefore) {
                // Must be responsible for some branch
                assert(responsibilities.size() > 0);
                toSave = true;
                why = why + "+cov";
            }

            // Save if new valid coverage is found
            if (this.validityFuzzing && validNonZeroAfter > validNonZeroBefore) {
                // Must be responsible for some branch
                assert(responsibilities.size() > 0);
                currentInput.valid = true;
                toSave = true;
                why = why + "+valid";
            }

            if (toSave) {

                // Trim input (remove unused keys)
                currentInput.gc();

                // It must still be non-empty
                assert(currentInput.size() > 0) : String.format("Empty input: %s", currentInput.desc);


                infoLog("Saving new input (at run %d): " +
                                "input #%d " +
                                "of size %d; " +
                                "total coverage = %d",
                        numTrials,
                        savedInputs.size(),
                        currentInput.size(),
                        nonZeroAfter);

                // Save input to queue and to disk
                try {
                    saveCurrentInput(responsibilities, why);
                } catch (IOException e) {
                    throw new GuidanceException(e);
                }

            }
        } else if (result == Result.FAILURE || result == Result.TIMEOUT) {
            String msg = error.getMessage();

            // Get the root cause of the failure
            Throwable rootCause = error;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }

            // Attempt to add this to the set of unique failures
            if (uniqueFailures.add(Arrays.asList(rootCause.getStackTrace()))) {

                // Trim input (remove unused keys)
                currentInput.gc();

                // It must still be non-empty
                assert(currentInput.size() > 0) : String.format("Empty input: %s", currentInput.desc);

                // Save crash to disk
                try {
                    int crashIdx = uniqueFailures.size()-1;
                    String saveFileName = String.format("id_%06d", crashIdx);
                    File saveFile = new File(savedFailuresDirectory, saveFileName);
                    writeCurrentInputToFile(saveFile);
                    infoLog("%s","Found crash: " + error.getClass() + " - " + (msg != null ? msg : ""));
                    String how = currentInput.desc;
                    String why = result == Result.FAILURE ? "+crash" : "+hang";
                    infoLog("Saved - %s %s %s", saveFile.getPath(), how, why);
                } catch (IOException e) {
                    throw new GuidanceException(e);
                }

            }
        }

        //       if (console != null) {
        //           displayStats();
        //       }

    }

    // Call only if console exists
//    private void displayStats() {
//        assert (console != null);
//
//        Date now = new Date();
//        long intervalMilliseconds = now.getTime() - lastRefreshTime.getTime();
//        if (intervalMilliseconds < STATS_REFRESH_TIME_PERIOD) {
//            return;
//        }
//        long interlvalTrials = numTrials - lastNumTrials;
//        long intervalExecsPerSec = interlvalTrials * 1000L / intervalMilliseconds;
//        double intervalExecsPerSecDouble = interlvalTrials * 1000.0 / intervalMilliseconds;
//        lastRefreshTime = now;
//        lastNumTrials = numTrials;
//        long elapsedMilliseconds = now.getTime() - startTime.getTime();
//        long execsPerSec = numTrials * 1000L / elapsedMilliseconds;
//
//        String currentParentInputDesc;
//        if (seedInputs.size() > 0 || savedInputs.isEmpty()) {
//            currentParentInputDesc = "<seed>";
//        } else {
//            Input currentParentInput = savedInputs.get(currentParentInputIdx);
//            currentParentInputDesc = currentParentInputIdx + " ";
//            currentParentInputDesc += currentParentInput.isFavored() ? "(favored)" : "(not favored)";
//            currentParentInputDesc += " {" + numChildrenGeneratedForCurrentParentInput +
//                    "/" + getTargetChildrenForParent(currentParentInput) + " mutations}";
//        }
//
//        int nonZeroCount = totalCoverage.getNonZeroCount();
//        double nonZeroFraction = nonZeroCount * 100.0 / totalCoverage.size();
//        int nonZeroValidCount = validCoverage.getNonZeroCount();
//        double nonZeroValidFraction = nonZeroValidCount * 100.0 / validCoverage.size();
//
//        console.printf("\033[2J");
//        console.printf("\033[H");
//        console.printf(this.getTitle() + "\n");
//        if (this.testName != null) {
//            console.printf("Test name:            %s\n", this.testName);
//        }
//        console.printf("Results directory:    %s\n", this.outputDirectory.getAbsolutePath());
//        console.printf("Elapsed time:         %s (%s)\n", millisToDuration(elapsedMilliseconds),
//                maxDurationMillis == Long.MAX_VALUE ? "no time limit" : ("max " + millisToDuration(maxDurationMillis)));
//        console.printf("Number of executions: %,d\n", numTrials);
//        console.printf("Valid inputs:         %,d (%.2f%%)\n", numValid, numValid*100.0/numTrials);
//        console.printf("Cycles completed:     %d\n", cyclesCompleted);
//        console.printf("Unique failures:      %,d\n", uniqueFailures.size());
//        console.printf("Queue size:           %,d (%,d favored last cycle)\n", savedInputs.size(), numFavoredLastCycle);
//        console.printf("Current parent input: %s\n", currentParentInputDesc);
//        console.printf("Execution speed:      %,d/sec now | %,d/sec overall\n", intervalExecsPerSec, execsPerSec);
//        console.printf("Total coverage:       %,d branches (%.2f%% of map)\n", nonZeroCount, nonZeroFraction);
//        console.printf("Valid coverage:       %,d branches (%.2f%% of map)\n", nonZeroValidCount, nonZeroValidFraction);
//
//        String plotData = String.format("%d, %d, %d, %d, %d, %d, %.2f%%, %d, %d, %d, %.2f, %d, %d, %.2f%%",
//                TimeUnit.MILLISECONDS.toSeconds(now.getTime()), cyclesCompleted, currentParentInputIdx,
//                numSavedInputs, 0, 0, nonZeroFraction, uniqueFailures.size(), 0, 0, intervalExecsPerSecDouble,
//                numValid, numTrials-numValid, nonZeroValidFraction);
//        appendLineToFile(statsFile, plotData);
//
//    }

    // Compute a set of branches for which the current input may assume responsibility
    private Set<Object> computeResponsibilities(boolean valid) {
        Set<Object> result = new HashSet<>();

        // This input is responsible for all new coverage
        Collection<?> newCoverage = runCoverage.computeNewCoverage(totalCoverage);
        if (newCoverage.size() > 0) {
            result.addAll(newCoverage);
        }

        // If valid, this input is responsible for all new valid coverage
        if (valid) {
            Collection<?> newValidCoverage = runCoverage.computeNewCoverage(validCoverage);
            if (newValidCoverage.size() > 0) {
                result.addAll(newValidCoverage);
            }
        }

        // Perhaps it can also steal responsibility from other inputs
        if (STEAL_RESPONSIBILITY) {
            int currentNonZeroCoverage = runCoverage.getNonZeroCount();
            int currentInputSize = currentInput.size();
            Set<?> covered = new HashSet<>(runCoverage.getCovered());

            // Search for a candidate to steal responsibility from
            candidate_search:
            for (Input candidate : savedInputs) {
                Set<?> responsibilities = candidate.responsibilities;

                // Candidates with no responsibility are not interesting
                if (responsibilities.isEmpty()) {
                    continue candidate_search;
                }

                // To avoid thrashing, only consider candidates with either
                // (1) strictly smaller total coverage or
                // (2) same total coverage but strictly larger size
                if (candidate.nonZeroCoverage < currentNonZeroCoverage ||
                        (candidate.nonZeroCoverage == currentNonZeroCoverage &&
                                currentInputSize < candidate.size())) {

                    // Check if we can steal all responsibilities from candidate
                    for (Object b : responsibilities) {
                        if (covered.contains(b) == false) {
                            // Cannot steal if this input does not cover something
                            // that the candidate is responsible for
                            continue candidate_search;
                        }
                    }
                    // If all of candidate's responsibilities are covered by the
                    // current input, then it can completely subsume the candidate
                    result.addAll(responsibilities);
                }

            }
        }

        return result;
    }

    @Override
    public Consumer<TraceEvent> generateCallBack(Thread thread) {
//        return getCoverage()::handleEvent;
        return (event) -> {
            System.out.println(String.format("Thread %s produced event %s",
                    thread.getName(), event));
        };
    }


    /**
     * Returns a reference to the coverage statistics.
     * @return a reference to the coverage statistics
     */
    public Coverage getCoverage() {
        if (coverage == null) {
            coverage = new Coverage();
        }
        return coverage;
    }

    public class LinearInput extends Input<Integer> {

        /**
         * A list of byte values (0-255) ordered by their index.
         */
        protected ArrayList<Integer> values;

        /**
         * The number of bytes requested so far
         */
        protected int requested = 0;

        public LinearInput() {
            super();
            this.values = new ArrayList<>();
        }

        public LinearInput(LinearInput other) {
            super(other);
            this.values = new ArrayList<>(other.values);
        }


        @Override
        public int getOrGenerateFresh(Integer key, Random random) {
            // Otherwise, make sure we are requesting just beyond the end-of-list
            // assert (key == values.size());
            //System.out.println("getOrGenerateFresh");
            if (key != requested) {
                throw new IllegalStateException(String.format("Bytes from linear input out of order. " +
                        "Size = %d, Key = %d", values.size(), key));
            }

            // Don't generate over the limit
            if (requested >= MAX_INPUT_SIZE) {
                return -1;
            }

            // If it exists in the list, return it
            if (key < values.size()) {
                requested++;
                // infoLog("Returning old byte at key=%d, total requested=%d", key, requested);
                return values.get(key);
            }

            // Handle end of stream
            if (GENERATE_EOF_WHEN_OUT) {
                return -1;
            } else {
                // Just generate a random input
                int val = random.nextInt(256);
                values.add(val);
                requested++;
                // infoLog("Generating fresh byte at key=%d, total requested=%d", key, requested);
                return val;
            }
        }

        @Override
        public int size() {
            return values.size();
        }

        /**
         * Truncates the input list to remove values that were never actually requested.
         *
         * <p>Although this operation mutates the underlying object, the effect should
         * not be externally visible (at least as long as the test executions are
         * deterministic).</p>
         */
        @Override
        public void gc() {
            // Remove elements beyond "requested"
            values = new ArrayList<>(values.subList(0, requested));
            values.trimToSize();
        }

        @Override
        public Input fuzz(Random random) {
            // Clone this input to create initial version of new child
            LinearInput newInput = new LinearInput(this);

            // Stack a bunch of mutations
            int numMutations = sampleGeometric(random, MEAN_MUTATION_COUNT);
            System.out.println("numMutations: "+numMutations);
            newInput.desc += ",havoc:"+numMutations;

            boolean setToZero = random.nextDouble() < 0.1; // one out of 10 times

            for (int mutation = 1; mutation <= numMutations; mutation++) {

                // Select a random offset and size
                int offset = random.nextInt(newInput.values.size());
                int mutationSize = sampleGeometric(random, MEAN_MUTATION_SIZE);

                // desc += String.format(":%d@%d", mutationSize, idx);

                // Mutate a contiguous set of bytes from offset
                for (int i = offset; i < offset + mutationSize; i++) {
                    // Don't go past end of list
                    if (i >= newInput.values.size()) {
                        break;
                    }

                    // Otherwise, apply a random mutation
                    int mutatedValue = setToZero ? 0 : random.nextInt(256);
                    newInput.values.set(i, mutatedValue);
                }
            }

            return newInput;
        }

        @Override
        public Iterator<Integer> iterator() {
            return values.iterator();
        }
    }

    /* Saves an interesting input to the queue. */
    protected void saveCurrentInput(Set<Object> responsibilities, String why) throws IOException {

        // First, save to disk (note: we issue IDs to everyone, but only write to disk  if valid)
        int newInputIdx = numSavedInputs++;
        String saveFileName = String.format("id_%06d", newInputIdx);
        String how = currentInput.desc;
        File saveFile = new File(savedInputsDirectory, saveFileName);
        if (SAVE_ONLY_VALID == false || currentInput.valid) {
            writeCurrentInputToFile(saveFile);
            infoLog("Saved - %s %s %s", saveFile.getPath(), how, why);
        }

        // If not using guidance, do nothing else
        if (blind) {
            return;
        }

        // Second, save to queue
        savedInputs.add(currentInput);

        // Third, store basic book-keeping data
        currentInput.id = newInputIdx;
        currentInput.saveFile = saveFile;
        currentInput.coverage = new Coverage(runCoverage);
        currentInput.nonZeroCoverage = runCoverage.getNonZeroCount();
        currentInput.offspring = 0;
        savedInputs.get(currentParentInputIdx).offspring += 1;

        // Fourth, assume responsibility for branches
        currentInput.responsibilities = responsibilities;
        for (Object b : responsibilities) {
            // If there is an old input that is responsible,
            // subsume it
            Input oldResponsible = responsibleInputs.get(b);
            if (oldResponsible != null) {
                oldResponsible.responsibilities.remove(b);
                // infoLog("-- Stealing responsibility for %s from input %d", b, oldResponsible.id);
            } else {
                // infoLog("-- Assuming new responsibility for %s", b);
            }
            // We are now responsible
            responsibleInputs.put(b, currentInput);
        }

    }

    private String millisToDuration(long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis % TimeUnit.MINUTES.toMillis(1));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis % TimeUnit.HOURS.toMillis(1));
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        String result = "";
        if (hours > 0) {
            result = hours + "h ";
        }
        if (hours > 0 || minutes > 0) {
            result += minutes + "m ";
        }
        result += seconds + "s";
        return result;
    }

    /** Returns the banner to be displayed on the status screen */
    protected String getTitle() {
        if (blind) {
            return  "Generator-based random fuzzing (no guidance)\n" +
                    "--------------------------------------------\n";
        } else {
            return  "Semantic Fuzzing with Zest\n" +
                    "--------------------------\n";
        }
    }

    public class SeedInput extends LinearInput
    {
        final File seedFile;
        final InputStream in;

        public SeedInput(File seedFile) throws IOException {
            super();
            this.seedFile = seedFile;
            this.in = new BufferedInputStream(new FileInputStream(seedFile));
            this.desc = "seed";
        }

        @Override
        public int getOrGenerateFresh(Integer key, Random random) {
            int value;
            try {
                value = in.read();
            } catch (IOException e) {
                throw new GuidanceException("Error reading from seed file: " + seedFile.getName(), e);

            }

            // assert (key == values.size())
            if (key != values.size()) {
                throw new IllegalStateException(String.format("Bytes from seed out of order. " +
                        "Size = %d, Key = %d", values.size(), key));
            }

            if (value >= 0) {
                requested++;
                values.add(value);
            }

            // If value is -1, then it is returned (as EOF) but not added to the list
            return value;
        }

        @Override
        public void gc() {
            super.gc();
            try {
                in.close();
            } catch (IOException e) {
                throw new GuidanceException("Error closing seed file:" + seedFile.getName(), e);
            }
        }

    }

}
