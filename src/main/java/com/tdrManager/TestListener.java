package com.tdrManager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


/**
 * 失败用例重跑此处是运用Listener，还有一种方法是运用Arrow插件（http://blog.sina.com.cn/s/blog_1324aaed20102w44l.html）
 */

public class TestListener extends TestListenerAdapter {
    private static Logger logger = Logger.getLogger( TestListener.class );
    private WebDriver driver;

    @Override
    public void onFinish(ITestContext context) {
        Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
        while (listOfFailedTests.hasNext()) {
            ITestResult failedTest = listOfFailedTests.next();
            ITestNGMethod method = failedTest.getMethod();
            if (context.getFailedTests().getResults( method ).size() > 1) {
                listOfFailedTests.remove();
            } else {
                if (context.getPassedTests().getResults( method ).size() > 0) {
                    listOfFailedTests.remove();
                }
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        super.onTestStart( result );
        logger.info( result.getName() + " Start" );


    }


    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        logger.info( result.getName() + " Success" );
        //super.onTestSuccess( result );
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO  Auto-generated method stub
        saveScreenShot( result );
        logger.info( result.getName() + " failure" );
        //super.onTestFailure( result );

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        saveScreenShot( result );
        logger.info( result.getName() + " skipped" );
        //super.onTestSkipped( result );
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub
    }


    //用例执行失败时截图
    private void saveScreenShot(ITestResult result) {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss" );
        String mDateTime = formatter.format( new Date() );
        String fileName = mDateTime + "_" + result.getName();
        String filePath = "";

        try {
            //这里可以调用不同框架的截图功能
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE );
            filePath = "screenshot/" + fileName + ".jpg";
            File destFile = new File( filePath );
            FileUtils.copyFile( screenshot, destFile );
        } catch (Exception e) {
            filePath = fileName + " tackScreentshot Failure:" + e.getMessage();
            logger.error( filePath );
        }

        if (!"".equals( filePath )) {
            Reporter.setCurrentTestResult( result );
            Reporter.log( filePath );
            //把截图写入到Html报告中方便查看
            Reporter.log( " + filePath + '\'/> " );
        }
    }
}

