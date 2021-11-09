package udfExtractor;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Logging {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public enum LogType {DEBUG, INFO, WARN};

    static void loginfo(LogType l, String log) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement lastinvoc = stackTraceElements[stackTraceElements.length - 1];
        String pattern = "HH:mm:ss yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        String color = null;
        String debug_level = null;
        switch (l) {
            case DEBUG:
                color = ANSI_GREEN;
                debug_level = "DEBUG";
                break;
            case INFO:
                color = ANSI_YELLOW;
                debug_level = "INFO";
                break;
            case WARN:
                color = ANSI_RED;
                debug_level = "WARN";
                break;
            default:
                color = ANSI_BLACK;
                debug_level = "";
                break;
        }

        System.out.println(color
                + "[" + date
                + " " + debug_level
                + " " + lastinvoc.getClassName() + " in " + lastinvoc.getFileName() + ":" + lastinvoc.getLineNumber()
                + "] " + log
                + ANSI_RESET);
    }



    static void logerr( String log) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement lastinvoc = stackTraceElements[stackTraceElements.length - 1];
        String pattern = "HH:mm:ss yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        String color = null;
        String debug_level = null;
                color = ANSI_RED;
                debug_level = "WARN";

        System.out.println(color
                + "[" + date
                + " " + debug_level
                + " " + lastinvoc.getClassName() + " in " + lastinvoc.getFileName() + ":" + lastinvoc.getLineNumber()
                + "] " + log
                + ANSI_RESET);
    }

    static void logdebug( String log) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement lastinvoc = stackTraceElements[stackTraceElements.length - 1];
        String pattern = "HH:mm:ss yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        String color = null;
        String debug_level = null;
                color = ANSI_GREEN;
                debug_level = "DEBUG";


        System.out.println(color
                + "[" + date
                + " " + debug_level
                + " " + lastinvoc.getClassName() + " in " + lastinvoc.getFileName() + ":" + lastinvoc.getLineNumber()
                + "] " + log
                + ANSI_RESET);
    }

    static void loginfo(String log) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement lastinvoc = stackTraceElements[stackTraceElements.length - 1];
        String pattern = "HH:mm:ss yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        String color = null;
        String debug_level = null;
                color = ANSI_YELLOW;
                debug_level = "INFO";


        System.out.println(color
                + "[" + date
                + " " + debug_level
                + " " + lastinvoc.getClassName() + " in " + lastinvoc.getFileName() + ":" + lastinvoc.getLineNumber()
                + "] " + log
                + ANSI_RESET);
    }


}