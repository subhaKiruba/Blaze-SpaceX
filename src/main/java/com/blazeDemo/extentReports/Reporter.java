package com.blazeDemo.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class houses few utilities required for the report
 */
public class Reporter {
    private static Map<String, Boolean> systemInfoKeyMap = new HashMap<>();

    private Reporter() {
        // Defeat instantiation
    }

    /**
     * Gets the {@link ExtentSparkReporter} instance created through listener
     *
     * @return The {@link ExtentSparkReporter} instance
     */
    public static ExtentSparkReporter getExtentHtmlReport() {
        return ExtentCucumberFormatter.getExtentHtmlReport();
    }

     /**
     * Gets the {@link ExtentReports} instance created through the listener
     *
     * @return The {@link ExtentReports} instance
     */
    public static ExtentReports getExtentReport() {
        return ExtentCucumberFormatter.getExtentReport();
    }

    /**
     * Loads the XML config file
     *
     * @param xmlPath The xml path in string
     * @throws IOException 
     */
    public static void loadXMLConfig(String xmlPath) throws IOException {
        getExtentHtmlReport().loadXMLConfig(xmlPath);
    }

    /**
     * Loads the XML config file
     *
     * @param file The file path of the XML. line# 56 is not supported in version 4.0.8
     */
    public static void loadXMLConfig(File file) {
        //getExtentHtmlReport().loadXMLConfig(file);
    }

    /**
     * Adds an info message to the current step
     *
     * @param message The message to be logged to the current step
     */
    public static void addStepLog(String message) {
        getCurrentStep().info(message);
    }

    /**
     * Adds an info message to the current scenario
     *
     * @param message The message to be logged to the current scenario
     */
    public static void addScenarioLog(String message) {
    	System.out.println("addScenarioLog::::"+message);
        getCurrentScenario().info(message);
    }

     /**
     * Sets the system information with the given key value pair
     *
     * @param key   The name of the key
     * @param value The value of the given key
     */
    public static void setSystemInfo(String key, String value) {
        if (systemInfoKeyMap.isEmpty() || !systemInfoKeyMap.containsKey(key)) {
            systemInfoKeyMap.put(key, false);
        }
        if (systemInfoKeyMap.get(key)) {
            return;
        }
        getExtentReport().setSystemInfo(key, value);
        systemInfoKeyMap.put(key, true);
    }

    /**
     * Sets the test runner output with the given list of strings
     *
     * @param log The list of string messages
     */
    public static void setTestRunnerOutput(List<String> log) {
    	System.out.println("LOGSSS::::"+log);
        getExtentReport().addTestRunnerOutput(log);
    }

    /**
     * Sets the test runner output with the given string
     *
     * @param outputMessage The message to be shown in the test runner output screen
     */
    public static void setTestRunnerOutput(String outputMessage) {
    	System.out.println("setTestRunnerOutput::::"+outputMessage);
        getExtentReport().addTestRunnerOutput(outputMessage);
    }

    /**
     * Sets the author name for the current scenario
     * @param authorName The author name of the current scenario
     */
    public static void assignAuthor(String... authorName) {
        getCurrentScenario().assignAuthor(authorName);
    }

    private static ExtentTest getCurrentStep() {
        return ExtentCucumberFormatter.stepTestThreadLocal.get();
    }

    private static ExtentTest getCurrentScenario() {
        return ExtentCucumberFormatter.scenarioThreadLocal.get();
    }
}