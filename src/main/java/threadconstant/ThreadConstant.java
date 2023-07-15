package threadconstant;

import java.util.HashMap;

import org.apache.commons.collections.map.MultiValueMap;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ThreadConstant {
	public WebDriver driver;
	public String browser;
	public String OS;
	public ExtentReports extent;
	public ExtentTest test;
	public String folderPath ;
	public boolean testCaseStatus;
	public boolean isRuleIdPresent;
	public boolean status;
	public String userId;
	public String TranId;
	public MultiValueMap map;
	public HashMap<String, String> dataMap;
	public String TestCaseName=null;
	public String runMode="Y";
}
