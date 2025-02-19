package com.basetest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.threadConst.TC;
import com.threadConst.ThreadInstance;
import com.utility.Constants;
import com.utility.ControlAction;
import com.utility.Credentials;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class TestBase {
    public String externalDate;
    public String extendReportName, extendReportName1, executionReportPath;
    public static String methodName, screenshotPath;
    static SimpleDateFormat formatter = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");
    public static Properties properies = new Properties();
    public static boolean retryFlagE2E = false;

    @BeforeSuite(alwaysRun = true)
    public void reportPathSetup() {
        File exeDir, passDir, faildir;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        Date sysdate = new Date();
        try {
            FileInputStream fis = new FileInputStream(Constants.CONFIG_FILE);
            properies.load(fis);
            Constants.Execution_REPORT_PATH = Constants.projectpath + "/Execution_Reports/" + formatter.format(sysdate) + "/";
            Constants.SCREENSHOT_PATH_FAIL = Constants.projectpath + "/Screeshots/Fail_" + formatter.format(sysdate)
                    + "/";
            exeDir = new File(Constants.Execution_REPORT_PATH);
            if (!exeDir.exists())
                exeDir.mkdirs();
            faildir = new File(Constants.SCREENSHOT_PATH_FAIL);
            if (!faildir.exists())
                faildir.mkdirs();


        } catch (Exception e) {
            Assert.fail("Failed while setting up report path");
        }

    }

    @BeforeTest(alwaysRun = true)
    public void instance(ITestContext context) {
        externalDate = new SimpleDateFormat().format(new Date());
        extendReportName = "ExecutionReport_" + context.getName() + "_" + System.getProperty("user.name") + formatter.format(new Date()) + ".html";
        executionReportPath = Constants.Execution_REPORT_PATH + extendReportName;
        TC.set(ThreadInstance.createInst());
        TC.get().extentReports = new ExtentReports(executionReportPath, true);
    }


    public WebDriver launchDriver(String browser, Method m) {
        try {
            switch (browser.toUpperCase()) {
                case "CHROME":
                    ChromeOptions option = new ChromeOptions();
                    if (properies.getProperty("headlessMode").equalsIgnoreCase("true")) {
                        option.addArguments("--headless");
                    } else {
                        option.addArguments("--incognito");
                    }
                    TC.get().driver = new ChromeDriver(option);
                    break;
                case "EDGE":

                    break;
                case "FIREFOX":
                    TC.get().driver = new FirefoxDriver();
                    break;
            }

            TC.get().driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            TC.get().driver.manage().window().maximize();
            TC.get().driver.manage().deleteAllCookies();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed while launching browser");
        }
        TC.get().controlAction = new ControlAction(TC.get().driver);
        return TC.get().driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method M) {
        TC.get().extentTest = TC.get().extentReports.startTest(M.getName());
    }

    public static void takeScreenshot(String status) {
        SimpleDateFormat formatter = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss_ms");
        Date sysDate = new Date();
        screenshotPath = Constants.SCREENSHOT_PATH_FAIL + formatter.format(sysDate) + ".png";
        String encodedBase64 = null;
        try {
            File src = ((TakesScreenshot) TC.get().driver).getScreenshotAs(OutputType.FILE);
            File finaldest = new File(screenshotPath);
            FileUtils.copyFile(src, finaldest);
            FileInputStream reader = new FileInputStream(finaldest);
            byte[] bytes = new byte[(int) finaldest.length()];
            reader.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encode(bytes));
            if (status.equalsIgnoreCase("Passed")) {
                TC.get().extentTest.log(LogStatus.PASS, TC.get().extentTest.addBase64ScreenShot("data:image/png;base64," + encodedBase64));
            } else {

                TC.get().extentTest.log(LogStatus.FAIL, TC.get().extentTest.addBase64ScreenShot("data:image/png;base64," + encodedBase64));
            }
        } catch (Exception e) {
            Assert.fail("Failed while adding screenshot to the report", e.getCause());
        }

    }

    public static Logger getLogger() {
        TC.get().logger = LogManager.getLogger(TestBase.class);
        return TC.get().logger;
    }

    public static void log4jInfo(String msg) {
        getLogger().info(msg);
        TC.get().extentTest.log(LogStatus.INFO, msg);
    }

    public static void log4jPass(String msg) {
        getLogger().info(msg);
        TC.get().extentTest.log(LogStatus.PASS, msg);
    }

    public static void log4jFail(String msg) {
        getLogger().info(msg);
        TC.get().extentTest.log(LogStatus.FAIL, msg);
    }

    public static void log4jPassExtended(String msg) {
        getLogger().info(msg);
        TC.get().extentTest.log(LogStatus.PASS, msg);
        takeScreenshot("Passed");
    }

    public static void log4jFailExtended(String msg) {
        getLogger().info(msg);
        TC.get().extentTest.log(LogStatus.FAIL, msg);
        takeScreenshot("Failed");
    }

    @AfterMethod(alwaysRun = true)
    public void endTestCase(Method M) {
        if (TC.get().driver != null) {
            TC.get().driver.close();
            //TC.get().driver.quit();
        }
        log4jInfo("********Execution completed for_" + M.getName() + "_ *****");
    }

    @AfterTest
    public void afterTest() {
        TC.get().extentReports.flush();
    }

    @BeforeTest
    @Parameters({"env"})
    public void credentialSetUp(@Optional("Q2") String env) {
        new Credentials(env);
    }

    @BeforeTest(alwaysRun = true)
    @Parameters("RetryFlag")
    public void getRetryFlagForE2E(@Optional("No") String retryFlag) {
        if (!retryFlag.equalsIgnoreCase("No")) {
            retryFlagE2E = true;
        }

    }
}
