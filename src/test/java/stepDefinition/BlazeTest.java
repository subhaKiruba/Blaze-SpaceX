package stepDefinition;

import com.blazeDemo.pages.actions.ChooseFlightsPage;
import com.blazeDemo.pages.actions.PassengerDetailsPage;
import com.blazeDemo.pages.actions.TicketInfoPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class BlazeTest {

	protected WebDriver driver;
	ChooseFlightsPage chooseFlights;
	PassengerDetailsPage passengerInfo;
	TicketInfoPage ticketInfo;

	@Before
	public void setupTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Given("^I launch the BlazeTravelAgency url$")
	public void launchURL() throws Throwable {
		chooseFlights = PageFactory.initElements(driver, ChooseFlightsPage.class);
		chooseFlights.setDriver(driver);
		chooseFlights.loadPage("https://blazedemo.com/index.php");
	}

	@And("^I verify welcome page components$")
	public void validateWelcomePageComponents() throws Throwable {
		chooseFlights.verifyWelcomePageComponents();
	}

	@Then("^I select \"([^\"]*)\" , \"([^\"]*)\" and click on findFlights button$")
	public void selectSourceAndDestination(String source, String destination) throws Throwable {
		chooseFlights.selectSourceDestination(source, destination);
	}

	@And("^I wait for \"([^\"]*)\" page to load$")
	public void waitForPageLoad(String page) throws Throwable {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Then("^I validate flights table header and values are not null$")
	public void validateFindFlightsTable() throws Throwable {
		chooseFlights.validateFindFlightsTable();
	}

	@And("^I select \"([^\"]*)\" by clicking on ChooseThisFlight button$")
	public void chooseFlights(String airline) throws Throwable {
		chooseFlights.chooseFlights(airline);
	}

	@And("^I click on PurchaseFlight button with the default passenger details$")
	public void updatePassengerInfoAndpurchaseFlight() throws Throwable {
		passengerInfo = PageFactory.initElements(driver, PassengerDetailsPage.class);
		passengerInfo.setDriver(driver);
		passengerInfo.updatePassengerInfoAndpurchaseFlight();
	}

	@And("^I validate whether ticket booking is successful$")
	public void validateTicketBooking() throws Throwable {
		ticketInfo = PageFactory.initElements(driver, TicketInfoPage.class);
		ticketInfo.setDriver(driver);
		ticketInfo.validateTicketBooking();
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
