package com.blazeDemo.pages.actions;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TicketInfoPage {
	static Logger log = Logger.getLogger(TicketInfoPage.class.getName());

	protected WebDriver driver;

	@FindBy(css = "div.container.hero-unit")
	public WebElement purchaseHeader;

	@FindBy(css = "table.table > tbody > tr")
	public List<WebElement> ticketInfoTable;

	public void setDriver(WebDriver driver) {
		// TODO Auto-generated method stub
		this.driver = driver;
	}

	public void validateTicketBooking() {
		// TODO Auto-generated method stub
		Assert.assertTrue("purchaseHeader not present", purchaseHeader.isDisplayed());

		List<String> tbody = ticketInfoTable.stream().map(e -> e.getText()).collect(Collectors.toList());
		log.info("Ticket Info::" + tbody.get(0));

	}

}