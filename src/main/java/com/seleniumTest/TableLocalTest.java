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

/*
自动化框架：
      关键字驱动，混合驱动，数据驱动
      selenium + java + maven + testNG + jenkins + log4j

       */
public class TableLocalTest {
    WebDriver driver;
    String tableUrl="D:\\ALIN\\selenium\\seleniumTable.html";

    //元素定位方法table核心：先定位到父节点再定位到子节点
    //打印班级为2班的学生姓名
    @Test
    public void  tableLocalTest(){
        driver.get(tableUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement table=driver.findElement(By.tagName("table"));
        WebElement tbody=table.findElement(By.tagName("tbody"));

        //findElement是定位单一的一个元素，要定位一组元素用findElements
        List<WebElement> trs=tbody.findElements(By.tagName("tr"));
        for (WebElement tr:trs){
            List<WebElement> tds=tr.findElements(By.tagName("td"));
            for (WebElement td:tds) {
                if (td.getText().equals("2班")) {
                    System.out.println(tds.get(1).getText()+"\n");
                }
            }
        }
    }

    @BeforeMethod
    public void startDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver=new ChromeDriver();
    }

    @AfterMethod
    public void quitDriver( ){
        driver.quit();
    }
}
