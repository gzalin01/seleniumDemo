package com.tdrManager;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Retry implements IRetryAnalyzer {
    public static Logger logger = Logger.getLogger( Retry.class );
    public int retryCount = 0;
    public static int maxRetryCount=2;//配置最大运行次数，失败后重跑maxRetryCount+1次
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount <= maxRetryCount) {
            String message = "running retry for '" + result.getName() + "' on class " +
                    this.getClass().getName() + " Retrying " + retryCount + " times";
            logger.info( message );
            Reporter.setCurrentTestResult( result );
            Reporter.log( "RunCount=" + (retryCount + 1) );
            retryCount++;
            return true;    //return true之后，代表继续重跑
        }
        return false;   //return false之后，代表停止重跑
    }
}
