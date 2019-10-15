package com.seleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SeleniumDemo {
    public WebDriver driver;
    String baseUrl = "http://www.baidu.com";

    @Test
    public void testSearch() {
        driver.get(baseUrl+"/");
        WebElement inputBox = driver.findElement(By.id("kw"));
        Assert.assertTrue(inputBox.isDisplayed());
        inputBox.sendKeys("CSDN");
        driver.findElement(By.id("su")).click();
    }

    //设置驱动所在位置
    @BeforeMethod
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    //退出浏览器
    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

}
