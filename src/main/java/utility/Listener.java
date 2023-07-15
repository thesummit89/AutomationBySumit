package utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

import com.relevantcodes.extentreports.IReporter;
import com.relevantcodes.extentreports.LogStatus;

import com.relevantcodes.extentreports.model.Test;

import basetest.TestBase;
import threadconstant.TC;


public class Listener extends TestBase implements ITestListener, IAnnotationTransformer{

	public void onTestStart(ITestResult result) {
		methodName = result.getMethod().getMethodName();
		
        log("*****************Execution Started for" + methodName + "***********************");


    }

    public void onTestSuccess(ITestResult result) {
        if (result.isSuccess()) {
            Reporter.log(result.getMethod().getMethodName() + "Test cases is Passed");
          
                TC.get().test.log(LogStatus.PASS, result.getMethod().getMethodName() + "Test Case is passed");
               // log4jInfo(result.getMethod().getMethodName() + " passed");
           
        }

    }
    
    public void onTestFailure(ITestResult result) {
        if (!result.isSuccess()) {
            Reporter.log(result.getMethod().getMethodName() + "Test cases is Failed");
           
                TC.get().test.log(LogStatus.FAIL, "Test step failed due to" + result.getThrowable());
                //log4jInfo(result.getMethod().getMethodName() + " failed");
                TestBase.takeScreenShot("Failed");//Parameterized takeScreenshotMethod
        }

    }


    public void onTestSkipped(ITestResult result) {
        Reporter.log(result.getMethod().getMethodName() + "Test cases skipped by Listeners due to :- " + result.getThrowable());
        if (skip == null) {
           

                TC.get().test.log(LogStatus.SKIP, "Test cases skipped by Listeners due to :- " + result.getThrowable());
                
            } 
        }

	
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    public void onFinish(ITestContext context) {

    }
    public void generateReport() {}



	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

	public void addTest(Test test) {
		// TODO Auto-generated method stub
		
	}

	public void setTestRunnerLogs() {
		// TODO Auto-generated method stub
		
	}

	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub

        IRetryAnalyzer rety = annotation.getRetryAnalyzer();

        if (rety == null) {
            annotation.setRetryAnalyzer(Retry.class);
        }
	}
}
