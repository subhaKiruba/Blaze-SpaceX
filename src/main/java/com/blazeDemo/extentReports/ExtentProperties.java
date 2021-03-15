package com.blazeDemo.extentReports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * An enum which holds the properties to be set for extent reporter
 */
public enum ExtentProperties {
	REPORTPATH;

	private String reportPath;

	ExtentProperties() {
		String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		this.reportPath = "ExtentReports" + File.separator + "TestResults_" + fileSuffix + ".html";

	}

	/**
	 * Gets the report path
	 * 
	 * @return The report path
	 */
	public String getReportPath() {
		return reportPath;
	}

}