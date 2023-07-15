package testcases;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import basetest.TestBase;
import pom.HomePage;
import threadconstant.TC;
import utility.ExcelReader;
import utility.TestUtils;

public class E2ETest extends TestBase {

	public WebDriver driver;
	TestUtils util = null;
	ExcelReader reader = null;
	HashMap<String ,String > dataMap=null;

	@BeforeTest
	public void getDataFromExcelSheet() {
		if (util == null) {
			util = new TestUtils();
			TC.get().map = util.loadMultiValMap("TestDataMasterSheet", "TestCase", "AutomationTest", "RunMode",
					"Runner");

		}

	}

	@BeforeMethod
	public void lauchUrl(Method m) {

		dataMap=TC.get().dataMap = util.loadTestDataMap("TestDataMasterSheet", "FieldName", m.getName().trim(), "TestData");
		String [] arr = TC.get().map.get(m.getName().trim()).toString().split(",");
        TC.get().TestCaseName=arr[0];
        TC.get().runMode=arr[1];
        
        if(!TC.get().runMode.equalsIgnoreCase("N")){
        	 startExtentReport(TC.get().TestCaseName);
     		 driver = launchBrowser();
        }else {
        	  throw new SkipException("Test case skipped due to run mode flag is N"+ TC.get().TestCaseName );
        }
       passLog("Browser launched successfully.");
	}

	@Test
	public void e2e_select_toAndFromCity() throws InterruptedException {

		HomePage page = new HomePage(driver);
		page.launchUrl().checkRoundTrip().selectFromCity(dataMap.get("fromcity")).selectToCity(dataMap.get("tocity"));// seleRoundTripDate("29/10/2023");
		passLog("Selected from city and to city successfully");

	}

	@Test
	public void e2e_searchFlight_toAndFromCity1() throws InterruptedException {

		HomePage page = new HomePage(driver);
		page.launchUrl()
		.checkRoundTrip()
		.selectFromCity(dataMap.get("fromcity"))
		.selectToCity(dataMap.get("tocity"))
		.seleRoundTripDate(dataMap.get("departureDate"), 
				dataMap.get("reutnDate")).searchFlight();
		
		Assert.assertEquals("Welcome", "Welcome to India", " . Mismatch Found for");

	}

}
