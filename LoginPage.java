package com.pom;

import com.basetest.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends TestBase {
    public WebDriver driver;
    Actions action =null;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        action = new Actions(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20),this);
    }

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    public WebElement searchBox;

    public void enterText(String str){
        searchBox.sendKeys(str);
        action.perform();
    }
}
