package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import basetest.TestBase;
import utility.ControlActions;
import utility.TestUtils;

public class HomePage extends TestBase {

	public WebDriver driver;
	ControlActions controlActions;
	TestUtils utils;

	public HomePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		controlActions = new ControlActions(driver);
	}

	@FindBy(xpath = Locators.ROUNDTRIP_XPATH)
	public WebElement roundTrip;
	@FindBy(xpath = Locators.TO_CITY)
	public WebElement toCity;
	@FindBy(xpath = Locators.TO_PLACEHOLDER_XPATH)
	public WebElement toCityInput;
	@FindBy(xpath = Locators.FROM_CITY)
	public WebElement fromCity;
	@FindBy(xpath = Locators.FROM_PLACEHOLDER_XPATH)
	public WebElement fromCityInput;
	@FindBy(xpath = Locators.DAY_PICKER_CAPTION_XPATH)
	public List<WebElement> dayPickerList;
	@FindBy(xpath = Locators.DEPARTURE_DATE)
	public WebElement depDate;
	@FindBy(xpath = Locators.NEXT_ARROW)
	public WebElement nextArrow;
	@FindBy(xpath = Locators.SEARCH_FLIGHT)
	public WebElement searchFlight;
	@FindBy(xpath=Locators.POP_UP_CLOSE)
	public WebElement popUp;
	@FindBy(xpath=Locators.RANDOM_CLICK)
	public WebElement randomClick;

	public HomePage launchUrl() {
		controlActions.getUrl(prop.getProperty("url"));
	//	controlActions.clickOnElement(popUp,"POP UP Close button");
		randomClick.click();
		return this;
	}

	public HomePage checkRoundTrip() {
		controlActions.clickOnElement(roundTrip, "Round Trip check box");
		return this;
	}

	public HomePage selectFromCity(String value) throws InterruptedException {
		controlActions.clickOnElement(fromCity, "From City");
		controlActions.selDrpDwnOnInpTextArrDwn(fromCityInput, value, "From City");
		return this;
	}

	public HomePage selectToCity(String value) throws InterruptedException {
		controlActions.clickOnElement(toCity, "To City");
		controlActions.selDrpDwnOnInpTextArrDwn(toCityInput, value, "To City");
		passLog("Selected from city and to city successfully");
		return this;
	}

	public HomePage selectDateForJourney(String date) {
		try {
			utils = new TestUtils();
			if (dayPickerList.size() ==0) {
				controlActions.clickOnElement(depDate, "Deparature date");
			} 
			String value = utils.getMonthAndyear(date);
			do {
				controlActions.clickOnElement(nextArrow, "Next Arrow");

			} while (!(dayPickerList.get(0).getText().equalsIgnoreCase(value)
					|| dayPickerList.get(1).getText().equalsIgnoreCase(value)));

			String depDate = utils.returnDateBasedonLocale(date);
			WebElement ele = driver.findElement(By.xpath(Locators.DATE_PICKER_XPATH1+depDate+Locators.DATE_PICKER_XPATH2));
			ele.click();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed while selecting departure date");
		}

		return this;
	}
	
	
	public HomePage seleRoundTripDate(String depDate,String returnDate) {
		selectDateForJourney(depDate);
		selectDateForJourney(returnDate);
		return this;
		
	}
	
	public HomePage searchFlight() {
		controlActions.clickOnElement(searchFlight,"SearchButton");
		return this;
	}

}
