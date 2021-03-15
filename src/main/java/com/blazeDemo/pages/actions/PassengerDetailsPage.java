package com.blazeDemo.pages.actions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PassengerDetailsPage {

	protected WebDriver driver;

	@FindBy(xpath = "*//body/div[2]/")
	public List<WebElement> flightDetails;

	@FindBy(xpath = "*//body/div[2]/form/")
	public List<WebElement> confirmationForm;

	@FindBy(css = "label.control-label")
	public List<WebElement> formLabels;

	@FindBy(css = "div.controls > input")
	public List<WebElement> formValues;

	@FindBy(css = "div.controls > input.btn.btn-primary")
	public WebElement purchaseFlight;

	public void setDriver(WebDriver driver) {
		// TODO Auto-generated method stub
		this.driver = driver;
	}

	public void updatePassengerInfoAndpurchaseFlight() {
		// TODO Auto-generated method stub
		purchaseFlight.click();
	}

}