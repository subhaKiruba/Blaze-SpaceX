package com.blazeDemo.pages.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ChooseFlightsPage {
	static Logger log = Logger.getLogger(ChooseFlightsPage.class.getName());

	protected WebDriver driver;

	@FindBy(css = "div.jumbotron > div > h1")
	public WebElement header;

	@FindBy(xpath = "//select[@name='fromPort']")
	public WebElement fromPort;

	@FindBy(xpath = "//select[@name='toPort']")
	public WebElement toPort;

	@FindBy(css = "input.btn.btn-primary")
	public WebElement findFlightsButton;

	@FindBy(css = "div.container > h3")
	public WebElement findFlightsHeader;

	@FindBy(css = "table.table > thead > tr > th")
	public List<WebElement> findFlightsTableHeader;

	@FindBy(css = "table.table > tbody > tr")
	public List<WebElement> findFlightsTableBody;

	@FindBy(css = "table.table > tbody > tr:nth-of-type(1) > td:nth-of-type(1) > input")
	public WebElement chooseFlightsButton;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void loadPage(String url) {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

	public void verifyWelcomePageComponents() throws Exception{
		Assert.assertTrue("Element Not present", header.isDisplayed());
		Assert.assertTrue("Element Not present", fromPort.isDisplayed());
		Assert.assertTrue("Element Not present", toPort.isDisplayed());
		Assert.assertTrue("Element Not present", findFlightsButton.isDisplayed());
	}

	public void selectSourceDestination(String source, String destination) {
		// TODO Auto-generated method stub
		Select elem;
		elem = new Select(fromPort);
		elem.selectByVisibleText(source);

		elem = new Select(toPort);
		elem.selectByVisibleText(destination);

		findFlightsButton.click();
	}

	public void validateFindFlightsTable() {
		// TODO Auto-generated method stub
		log.info("TH:::" + findFlightsTableHeader.size());
		Assert.assertEquals(6, findFlightsTableHeader.size());
		ArrayList<String> thead = new ArrayList<String>();
		thead.add("Choose");
		thead.add("Flight #");
		thead.add("Airline");
		thead.add("Departs");
		thead.add("Arrives");
		thead.add("Price");

		List<String> thText = findFlightsTableHeader.stream().map(e -> e.getText()).collect(Collectors.toList());
		log.info("thText::" + thText);
		log.info("tbody:::" + findFlightsTableBody.size());
		Assert.assertEquals(5, findFlightsTableBody.size());

	}

	public void chooseFlights(String airline) {
		// TODO Auto-generated method stub
		chooseFlightsButton.click();
	}
}