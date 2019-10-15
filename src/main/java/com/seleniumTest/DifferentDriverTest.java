package com.seleniumTest;

import com.seleniumUtil.ScreenShotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import org.testng.annotations.Test;

public class DifferentDriverTest {
    public WebDriver driver;
    String url="http://www.baidu.com";

    //测试火狐浏览器
    @Test
    public void testFireFoxDriver() {
        driver.get(url);
    }

    @BeforeMethod
    public void beforeMethod(){
        //火狐的安装位置
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        //加载火狐驱动
        System.setProperty("webdriver.firefox.marionette","C:\\Program Files\\Mozilla Firefox\\geckodriver.exe");
        driver=new FirefoxDriver();
        //报错时截屏保存
        ScreenShotUtil util=new ScreenShotUtil(driver);
        util.takeScreenshot(driver);

    }

    @AfterMethod
    public void afterMethod(){
        driver.close();
    }



}
