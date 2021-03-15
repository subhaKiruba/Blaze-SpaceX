package runners;

import org.junit.runner.RunWith;

import cucumber.api.*;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = "stepDefinitionAPI", tags = {
		"@SpacXAPITesting" }, monochrome = true, plugin = { "pretty",
				"com.blazeDemo.extentReports.ExtentCucumberFormatter:", "junit:target/cucumber-reports/cucumber.xml" })

public class RunnerAPI {

}