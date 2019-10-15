package com.seleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElementLocalTest {
    WebDriver driver;
    String url="http://www.baidu.com";

    @Test
    public void electmentLocalTest() throws InterruptedException {
        //窗口最大化
        driver.manage().window().maximize();
        //等待
        //driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        driver.get(url);
        //元素定位方法By.id
        //driver.findElement(By.id("kw")).sendKeys("selenium id");
        //元素定位方法By.name
        //driver.findElement(By.name("wd")).sendKeys("selenium name");
        //元素定位方法By.class
        //driver.findElement(By.className("s_ipt")).sendKeys("selenium className");
        //元素定位方法By.tagName
        /*
        List<WebElement> allInputs = driver.findElements(By.tagName("input"));
        System.out.println("size="+allInputs.size());
        for(WebElement element: allInputs){
            if (element.getAttribute("class").equals("s_ipt")){
                String elementTagName=element.getTagName();
                driver.findElement(By.tagName(elementTagName)).sendKeys("tagName");
            }
        }
        */
        //元素定位方法By.linkText
        //driver.findElement(By.linkText("新闻")).click();
        //元素定位方法By.partialLinkText
        //driver.findElement(By.partialLinkText("hao")).click();
        //元素定位方法By.xpath  ctrl+shift+x  定位需要获取xpth的位置--》右键--》copy--》copy XPath
        //driver.findElement(By.xpath("//*[@id=\"kw\"]")).sendKeys("xpath 定位");
        //元素定位方法By.cssSelector
        //driver.findElement(By.cssSelector("#kw")).sendKeys("css");
        //元素定位方法table
        //定位到【百度一下】按钮
        driver.findElement(By.id("su")).click();
        Thread.sleep(1000);
    }

    @BeforeMethod
    public void startDriver(){
        //指定chromedriver的存放路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        //实例化driver
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void closeDriver(){
        driver.quit();
    }
}
