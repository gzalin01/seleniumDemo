package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.tdrManager.GetToken.getToken;


public class TdrManagerBasic {
    public static WebDriver driver;
    public static String tdrManagerIndexUrl = "http://123.124.21.115:8081/portal/index.html#/home";
    private static final Logger logger = Logger.getLogger( TdrManagerBasic.class );
    public static Actions actions;

    public static void tdrManagerBasic() {
        //窗口最大化
        driver.manage().window().maximize();
        driver.navigate().to( tdrManagerIndexUrl );
        //隐性等待
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        actions = new Actions( driver );

        //获取token并添加cookie
        String token = getToken();
        System.out.println( "token:" + token );
        if (token != null) {
            Cookie cookie = new Cookie( "token", token );
            driver.manage().addCookie( cookie );
            driver.navigate().refresh();
            try {
                Thread.sleep( 5000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断是否有效token（过期）
            boolean isExist=ElementExist(By.xpath( "/html/body/div[1]/aside/div/ul/li[1]/span" ));
            Assert.assertTrue( isExist );

        } else {
            logger.info( "登录失败，未获取到token" );
        }
    }


     public static boolean ElementExist(By Locator) {
        try {
            driver.findElement( Locator );
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    @BeforeSuite
    public void startDriver() {
        System.setProperty( "webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe" );
        driver = new ChromeDriver();
    }

    @AfterSuite
    public void quitDriver() {
        driver.close();
    }

}
