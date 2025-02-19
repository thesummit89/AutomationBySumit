package com.utility;

import com.basetest.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import com.threadConst.TC;
import org.testng.*;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Listener extends TestBase implements ITestListener, IAnnotationTransformer, IReporter {

    public void onTestStart(ITestResult result){
        methodName= result.getMethod().getMethodName();
        log4jInfo("*****************Execution Started for" + methodName + "***********************");
    }
    public void onTestSuccess(ITestResult result) {
        if(result.isSuccess()){
            TC.get().extentTest.log(LogStatus.PASS, result.getMethod().getMethodName() + "Test Case is passed");
        }
    }
    public void onTestFailure(ITestResult result) {
        if(!result.isSuccess()){
            TC.get().extentTest.log(LogStatus.FAIL, result.getMethod().getMethodName() + "Test Case is FAILED");
            takeScreenshot("Failed");

        }
    }
    public void onTestSkipped(ITestResult result) {
        try {
            TC.get().extentTest.log(LogStatus.WARNING, "First run for test case is skipped/failed  by Listeners due to :- " + result.getThrowable());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(Retry.class);
    }

}
