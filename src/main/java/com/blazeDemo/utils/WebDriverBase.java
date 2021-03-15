package com.blazeDemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverBase {
	protected WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@Before
    public void setupTest() {
    	 WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        setDriver(driver);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    
    }