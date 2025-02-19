package com.utility;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ControlAction {
    public WebDriver driver;
    Actions action;
    public WebDriverWait wait;

    public ControlAction(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
        } catch (StaleElementReferenceException e) {
            Assert.fail("Stale element reference. " + element, e.getCause());
        } catch (Exception e) {
            Assert.fail("Element not displayed - " + element, e.getCause());
        }
    }

    public void getUrl(String url) {
        try {
            driver.get(url);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        } catch (Exception e) {
            Assert.fail("Page not loaded within 20 Seconds. URL - " + url, e.fillInStackTrace());
        }

    }

    public void isElementClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            Assert.fail("Element not clickable withing given time seconds ", e.getCause());
        }
    }

    public void actionClickButton(WebElement element) {
        try {
            isElementClickable(element);
            action.click(element);
            action.perform();
        } catch (ElementNotInteractableException e) {
            Assert.fail("Issue while clicking on element - " + element, e.getCause());

        } catch (StaleElementReferenceException e) {

            Assert.fail("Stale element reference for " + element, e.getCause());

        } catch (Exception e) {

            Assert.fail("Issue while clicking on web element - " + element, e.getCause());

        }

    }

    public void setTextBox(WebElement element, String value) {
        try {
            isElementPresent(element);
            element.sendKeys(value);
        } catch (Exception e) {
            Assert.fail("Failed while entering value into text box", e.fillInStackTrace());
        }

    }
    public void selectDropDownByValue(WebElement element, String value){
        try {
            isElementPresent(element);
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            Assert.fail("Failed while entering value into text box", e.fillInStackTrace());
        }
    }
}