package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import basetest.TestBase;

public class ContactUs extends TestBase{
	
	public ContactUs() {
	
		PageFactory.initElements(driver, this);
	}
	
    @FindBy(xpath = Locators.CONTACT_XPATH)
    public WebElement contact;
    
    public ContactUs clickOnContact() {
    	contact.click();
    	
    	return this;
    }
}
