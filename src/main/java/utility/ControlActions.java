package utility;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basetest.TestBase;

public class ControlActions extends TestBase {
	public WebDriver driver;
	public WebDriverWait wait;
	Actions action;

	public ControlActions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 20);
		action = new Actions(driver);
	}

	public boolean isElementClickable(WebElement ele, String field) {
		try {
			ele.isDisplayed();
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (ElementNotVisibleException e) {
			Assert.fail("Failed due element not visible for field =>" + field);
		}

		catch (ElementNotInteractableException e) {
			Assert.fail("Failed due element not clickable for field =>" + field);
		} catch (Exception e) {
			Assert.fail("Failed due element ---- for field =>" + field);
		}
		return true;

	}

	public void getUrl(String url) {
		try {
			driver.get(url);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
	}

	/**
	 * 
	 * @param ele
	 * @param field
	 * @method- it helps to click on element
	 */
	public void clickOnElement(WebElement ele, String field) {
		try {
			if(isElementClickable(ele, field)) {
			ele.click();}
		} catch (Exception e) {
			Assert.fail("---- for field =>" + field);
		}

	}

	public void sendKeys(WebElement ele, String value, String field) {
		try {
			if(isElementClickable(ele, field)) {
			ele.sendKeys(value);}
		} catch (Exception e) {
			Assert.fail("Failed while entering value " + value + " into field " + field + " PFA locator:- " + ele);
		}

	}

	/*******
	 * @method- selects value from dropdown by doing Key Arrow down
	 * @param ele
	 * @param value
	 * @param field
	 ******/
	public void selDrpDwnOnInpTextArrDwn(WebElement ele, String value, String field) {

		try {
			// li[@role='option' and @aria-selected='true']

			
			  action.moveToElement(ele).click().build().perform();
			  action.sendKeys(value).perform();
			  Thread.sleep(3000);
			  //wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='makeFlex hrtlCenter']//img"))));
			  action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
			  
			 
		} catch (Exception e) {
			Assert.fail("Failed while selecting dropdown");
		}
	}
	
	public WebElement getWebElement(String xpath ) {
		return driver.findElement(By.xpath(xpath));
	}

}
