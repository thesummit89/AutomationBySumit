package com.utility;

public class Constants {
    public static String Execution_REPORT_PATH,
            SCREENSHOT_PATH_FAIL,
            SCREENSHOT_PATH_PASS, Execution_REPORT_PATH_API, Execution_REPORT_PATH_RULESAPI_CSV;
    final public static String CONFIG_FILE = System.getProperty("user.dir")
            + "/src/main/resources/config.properties";
    final public static String projectpath = System.getProperty("user.dir") + "/test-output/reports";
    final public static String SPARK_CONFIG = System.getProperty("user.dir") + " \\src\\main\\resources\\extent.properties";

}