package basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import threadconstant.TC;
import threadconstant.ThreadConstant;
import threadconstant.ThreadInst;
import utility.Constant;

import utility.ExcelReader;
import utility.TestUtils;

public class TestBase {

	public static Properties prop;
	public File file = null;
	public SimpleDateFormat dateFormat;
	public WebDriver driver;
	// public String screenshotPath;
	public String externalDate;
	public static String methodName, erroMsg, Testcase, persona, sheetName, path, screenshotPath, skip = null;
	// public WebDriver driver; // static , nonstatic

	public String extendReportName, extendReportName1, executionReportPath;
	static SimpleDateFormat formatter = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");
	static Date sysdate = new Date();
	// public static Properties prop = new Properties();
	public static TestUtils utils = new TestUtils();
	public static ExcelReader excel;
	public static File f;
	public HashMap<String, String> config = new HashMap<String, String>();
	public static String testMethodName;
//	public ExtentReports reports;
	//public ExtentTest test;
	

	/**
	 * @author - Sumit B Devloped on 13th June 2023
	 * @reportSetup - This method return configuration
	 ***/
	@BeforeSuite
	public void reportSetUp() {
		try {
			if (prop == null) {
				prop = new Properties();
			}
			FileInputStream fis = new FileInputStream(new File(Constant.CONFIG_PATH));
			prop.load(fis);
		} catch (FileNotFoundException e) {
			Assert.fail("Failed because of File not found at location. PFA logs " + "\n" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed because of configuration. PFA logs " + "\n" + e.getMessage());
		}
       createFolderPath();
	}

	/**
	 * @author - Sumit B Devloped on 1th June 2023
	 * @reportSetup -
	 ***/

	private void createFolderPath() {

		Date sysDate = new Date();
		dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		File exedir, faildir;
		try {

			Constant.EXECUTION_REPORT_PATH = Constant.PROJECTPATH + "/ExecutionReport/" + dateFormat.format(sysDate)+"/";
			Constant.SCREENSHOT_PATH_FAIL = Constant.PROJECTPATH + "/Screeshots/Fail_" + dateFormat.format(sysDate)
            + "/";

			exedir = new File(Constant.EXECUTION_REPORT_PATH);

			if (!exedir.exists()) {
				exedir.mkdir();
			}

			faildir = new File(Constant.SCREENSHOT_PATH_FAIL);
			if (!faildir.exists())
				faildir.mkdirs();

		} catch (Exception e) {
			Assert.fail("Folder not created" + e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 */
	public WebDriver launchBrowser() {

		try {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/resources/chromeDriver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.merge(capabilities);
			TC.get().driver = new ChromeDriver(options);
			TC.get().driver.manage().window().maximize();
			TC.get().driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			TC.get().driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		} catch (SessionNotCreatedException e) {
			Assert.fail("Failed because of version mismatch" + e.getMessage());
		}

		catch (Exception e) {

			Assert.fail("Failed while Launching Browser" + e.getMessage());
		}
		return TC.get().driver;

	}

	//@BeforeMethod(alwaysRun = true)
	public void startExtentReport(String name) {
		TC.get().test = TC.get().extent.startTest(name);

	}

	@BeforeTest
	public void instance(ITestContext context) {
		externalDate = new SimpleDateFormat().format(new Date());
		extendReportName = "ExecutionReport_" + context.getName() + "_" + System.getProperty("user.name")
		+ formatter.format(sysdate) + ".html";
		executionReportPath = Constant.EXECUTION_REPORT_PATH+ extendReportName;
		ThreadConstant tc = ThreadInst.createInstanse();
		TC.set(tc);
		TC.get().extent = new ExtentReports(executionReportPath, true); //1
		
	}

	@AfterTest
	public void afterTest() {

		TC.get().extent.flush(); // you have to always flush extent report at the end . 
		if (TC.get().driver != null) {
			// TC.get().driver.quit();
		}
	}

	@AfterMethod
	public void endTestCase(Method M) {
		try {
			if (TC.get().driver != null) {
				TC.get().driver.close();
				TC.get().driver.quit();
			}
			// logout method
			log("********Execution completed for_" + M.getName() + "_ *****");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	/*
	 * @Purpose - This method 'passLog' is used to update the pass status in the
	 * extent report wherever this method is called
	 */
	public void passLog(String mesg) {
		try {
			TC.get().test.log(LogStatus.PASS, mesg);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void passLogWithScreenShot(String mesg) {
		try {
			TC.get().test.log(LogStatus.PASS, mesg);
			takeScreenShot("Passed");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * @Purpose - This method 'log' is used to update the info status in the extent
	 * report wherever this method is called
	 */
	public void log(String mesg) {
		try {
			TC.get().test.log(LogStatus.INFO, mesg);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * @Purpose - This method 'log' is used to update the info status in the extent
	 * report wherever this method is called
	 */
	// TODO Explain how to use this method and its usage
	public void failLog(String mesg) {
		try {
			TC.get().test.log(LogStatus.FAIL, mesg);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void takeScreenShot(String status) {
        SimpleDateFormat formatter = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss_ms");
        Date sysdate = new Date();
        screenshotPath = Constant.SCREENSHOT_PATH_FAIL + formatter.format(sysdate) + ".png";
        String encodedBase64 = null;
        try {
            File src = ((TakesScreenshot) TC.get().driver).getScreenshotAs(OutputType.FILE);
            File finalDest=  new File(screenshotPath);
            FileUtils.copyFile(src,finalDest );
            FileInputStream reader = new FileInputStream(finalDest);
            byte[] bytes = new byte[(int) finalDest.length()];
            
            reader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            
            
             
                if(status.equalsIgnoreCase("Passed")){
                	//TC.get().test.log(LogStatus.PASS, TC.get().test.addScreenCapture(screenshotPath));	
                	TC.get().test.log(LogStatus.PASS, TC.get().test.addBase64ScreenShot("data:image/png;base64," + encodedBase64));	
                }else{
                	TC.get().test.log(LogStatus.FAIL, TC.get().test.addScreenCapture("data:image/png;base64," + encodedBase64));
                	}

        } catch (Exception e) {
            e.getMessage();
        }
    }

}
