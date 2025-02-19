package com.threadConst;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.utility.ControlAction;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ThreadConstants {
    public WebDriver driver;
    public String browser;
    public String OS;
    public ExtentReports extentReports;
    public ExtentTest extentTest;
    public String CycleId;
    public String issueKey;
    public int totalTestPassed = 0, totalTestFailed = 0, totalTestSkipped = 0, totalTestCount;
    public String folderPath ;
    public boolean testCaseStatus;
    public boolean isRuleIdPresent;
    public boolean status;
    public String ruleUuid;
    public String loanNum;
    public String jsonWithSetAttributes;
    public List<Map<String, String>> rowList;
    public int rowNum;
    public double timeUnit;
    public String googleSheetGID;
    public String downloadFilepathULDD;
    public String resultLinkULDD="No";
    public String noLinkForOtheFailure="No";
    public String freddieMacException="";
    public String xmlValidation="";
    public Logger logger;
    public Properties properies;
    public ControlAction controlAction;

}
