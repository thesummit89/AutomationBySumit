package com.utility;

import com.basetest.TestBase;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry extends TestBase implements IRetryAnalyzer {
    private int retryCount=0;
    private int maxRetryCount=1;


    @Override
    public boolean retry(ITestResult result) {
        if(retryFlagE2E) {
            if (retryCount < maxRetryCount) {
                log4jInfo("Retrying " + result.getName() + " test cases with status " + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time");
                retryCount++;
                return true;
            }
        }
        return false;
    }



    public String getResultStatusName(int status){
      String resultName=null;
      if(status ==1){
          resultName="SUCCESS";
      }
      else if(status==2){
          resultName="FAILURE";
      }
      else{
          resultName="SKIP";
      }
      return resultName;
    }

}
