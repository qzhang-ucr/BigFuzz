package edu.ucla.cs.jqf.bigfuzz;


import com.google.common.io.CharStreams;
import edu.berkeley.cs.jqf.fuzz.guidance.Guidance;
import edu.berkeley.cs.jqf.fuzz.guidance.GuidanceException;
import edu.berkeley.cs.jqf.fuzz.guidance.Result;
import edu.berkeley.cs.jqf.fuzz.util.Coverage;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimplifiedBigFuzzSalaryGuidance implements Guidance {

    SalaryAnalysisMutation salaryAnalysisMutation  = new SalaryAnalysisMutation();

    long maxTrails = 1;
    long countTrails = 0;
    String rootFile = "";
    private Coverage coverage;

    public SimplifiedBigFuzzSalaryGuidance(String rootFile, long maxTrails, PrintStream out)
    {
        this.rootFile = rootFile;
        this.maxTrails = maxTrails;
    }
    public String convert(InputStream inputStream, Charset charset) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    @Override
    public InputStream getInput() throws IllegalStateException, GuidanceException {
        System.out.println("getInput");
        //runCoverage.clear();

        // Choose an input to execute based on state of queues
        String initialString = "中华人民共和国成立了";
        System.out.println(initialString);
        //InputStream input;
        //input.read();
        InputStream targetStream;
        //try
        //{
            //System.out.println("UTF8");
            targetStream = new ByteArrayInputStream(initialString.getBytes());
        //}
        //catch (UnsupportedEncodingException e)
        //{
        //    System.out.println("UTF8 unsupported");
        //    targetStream = new ByteArrayInputStream(initialString.getBytes());
        //}

        //String originalString = randomAlphabetic(8);
        //InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());
        try{
            String outString = this.convert(targetStream, StandardCharsets.UTF_8);
            System.out.println("Out: "+outString);
        }
        catch (IOException e)
        {

        }

        return targetStream;
        /*return new InputStream() {
            int bytesRead = 0;
            String initialString = "text1111111";
            byte[] bytes;
            String initialString = "text1111111";
            byte[] bytes;
            try
            {
                bytes = initialString.getBytes("UTF8");
            }
            catch (UnsupportedEncodingException e)
            {
                bytes = initialString.getBytes();
            }
            @Override
            public int read() throws IOException {
                //int ret = 1;

                //bytesRead++;
                //System.out.println("read("+bytesRead+") = "+ret);
                //System.out.println("Bytes size: "+bytes.length);
                if(bytesRead>=bytes.length)
                {
                    return -1;
                }
                int ret = bytes[bytesRead];
                bytesRead++;
                return ret;
            }
        };*/
    }

    @Override
    public boolean hasInput() {
        if(countTrails<maxTrails)
        {
            System.out.println("HasInput");
            return true;
        }
        System.out.println("Has No Input");
        return false;
    }

    @Override
    public void handleResult(Result result, Throwable error) throws GuidanceException {
        this.countTrails++;
    }

    @Override
    public Consumer<TraceEvent> generateCallBack(Thread thread) {
        return (event) -> {
            System.out.println(String.format("Thread %s produced event %s",
                    thread.getName(), event));
        };
    }
    public Coverage getCoverage() {
        if (coverage == null) {
            coverage = new Coverage();
        }
        return coverage;
    }
}
