package com.blazeDemo.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * A cucumber based reporting listener which generates the Extent Report
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {
    private static ExtentReports extentReports;
    private static ExtentSparkReporter htmlReporter;
    private static ThreadLocal<ExtentTest> featureTestThreadLocal = new InheritableThreadLocal<>();
    // private static ThreadLocal<ExtentTest> scenarioOutlineThreadLocal = new
    // InheritableThreadLocal<>();
    static ThreadLocal<ExtentTest> scenarioThreadLocal = new InheritableThreadLocal<>();
    private static ThreadLocal<LinkedList<Step>> stepListThreadLocal = new InheritableThreadLocal<>();
    static ThreadLocal<ExtentTest> stepTestThreadLocal = new InheritableThreadLocal<>();
    private boolean scenarioOutlineFlag;
    
    protected WebDriver driver;
    
    public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

    public ExtentCucumberFormatter(File file)  {
        setExtentHtmlReport(file);
        try {
			setExtentReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        stepListThreadLocal.set(new LinkedList<Step>());
        scenarioOutlineFlag = false;
    }

    private static void setExtentHtmlReport(File file) {
        if (htmlReporter != null) {
            return;
        }
        if (file == null || file.getPath().isEmpty()) {
            file = new File(ExtentProperties.REPORTPATH.getReportPath());
        }
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        htmlReporter = new ExtentSparkReporter(file);
    }

    static ExtentSparkReporter getExtentHtmlReport() {
        return htmlReporter;
    }

    private static void setExtentReport() throws IOException {
        if (extentReports != null) {
            return;
        }
        extentReports = new ExtentReports();

        htmlReporter.loadXMLConfig("src\\main\\java\\com\\blazeDemo\\extentReports\\bdd-config.xml");
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("Environment", "Demo");
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
    }

    static ExtentReports getExtentReport() {
        return extentReports;
    }

    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    public void uri(String uri) {

    }

    public void feature(Feature feature) {
        featureTestThreadLocal.set(getExtentReport()
                .createTest(com.aventstack.extentreports.gherkin.model.Feature.class, feature.getName()));
        ExtentTest test = featureTestThreadLocal.get();

        for (Tag tag : feature.getTags()) {
            test.assignCategory(tag.getName());
        }
    }

    /**
     * The below code can be enabled for features with ScenarioOutline
     **/

    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    /**
     * The below code can be enabled for ScenarioOutline with examples
     **/
    public void examples(Examples examples) {

    }

    /**
     * Tags are not used in this repo currently. May be enabled later
     **/

    public void startOfScenarioLifeCycle(Scenario scenario) {
        if (scenarioOutlineFlag) {
            scenarioOutlineFlag = false;
        }

        ExtentTest scenarioNode;

        scenarioNode = featureTestThreadLocal.get()
                .createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());

        scenarioThreadLocal.set(scenarioNode);
    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {
        if (scenarioOutlineFlag) {
            return;
        }
        stepListThreadLocal.get().add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    public void done() {
        getExtentReport().flush();
    }

    public void close() {
    	
    }

    public void eof() {

    }

    public void before(Match match, Result result) {

    }

    public void result(Result result) {
        if (scenarioOutlineFlag) {
            return;
        }

        if (Result.PASSED.equals(result.getStatus())) {
            stepTestThreadLocal.get().pass(Result.PASSED);
        } else if (Result.FAILED.equals(result.getStatus())) {
        	stepTestThreadLocal.get().fail(result.getError().getLocalizedMessage());
        } else if (Result.SKIPPED.equals(result)) {
            stepTestThreadLocal.get().skip(Result.SKIPPED.getStatus());
        } else if (Result.UNDEFINED.equals(result)) {
            stepTestThreadLocal.get().skip(Result.UNDEFINED.getStatus());
        }
    }

    public void after(Match match, Result result) {

    }

    public void match(Match match) {
        Step step = stepListThreadLocal.get().poll();
        String data[][] = null;
        if (step.getRows() != null) {
            List<DataTableRow> rows = step.getRows();
            int rowSize = rows.size();
            for (int i = 0; i < rowSize; i++) {
                DataTableRow dataTableRow = rows.get(i);
                List<String> cells = dataTableRow.getCells();
                int cellSize = cells.size();
                if (data == null) {
                    data = new String[rowSize][cellSize];
                }
                for (int j = 0; j < cellSize; j++) {
                    data[i][j] = cells.get(j);
                }
            }
        }

        ExtentTest scenarioTest = scenarioThreadLocal.get();
        ExtentTest stepTest = null;

        try {
            stepTest = scenarioTest.createNode(new GherkinKeyword(step.getKeyword()), step.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (data != null) {
            Markup table = MarkupHelper.createTable(data);
            stepTest.info(table);
        }

        stepTestThreadLocal.set(stepTest);
    }

    public void embedding(String mimeType, byte[] data) {

    }

    public void write(String text) {

    }
}