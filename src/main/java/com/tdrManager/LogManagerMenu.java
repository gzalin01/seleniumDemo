package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogManagerMenu extends TdrManagerBasic {


    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    //系统日志
    public static void SystemLogMenu() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到日志管理菜单
        WebElement logMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/div" ) );
        actions.moveToElement( logMenu ).click( logMenu ).perform();
        //定位到系统日志菜单
        WebElement systemLog = driver.findElement( By.xpath( "" ) );
        actions.moveToElement( systemLog ).click( systemLog ).perform();

        // *************************查询功能


        //************************翻页功能
        String totalNumberText = driver.findElement( By.xpath( "" ) ).getText().replaceAll( "[^0-9]", "" );
        int totalNumber = Integer.parseInt( totalNumberText );
        if (totalNumber > 10) {
            //定位到下一页按钮
            WebElement nextPage = driver.findElement( By.xpath( "" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            WebElement element = driver.findElement( By.xpath( "" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            Logger.getLogger( LogManagerMenu.class ).info( "系统日志列表数据不足10条" );
        }
    }


    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    //外部系统调用
    public static void accessApiLogMenu() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到日志管理菜单
        WebElement logMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/div" ) );
        actions.moveToElement( logMenu ).click( logMenu ).perform();
        //定位到外部系统调用菜单
        WebElement accessApiLog = driver.findElement( By.xpath( "" ) );
        actions.moveToElement( accessApiLog ).click( accessApiLog ).perform();
        // *************************查询功能


        //************************翻页功能
        String totalNumberText = driver.findElement( By.xpath( "" ) ).getText().replaceAll( "[^0-9]", "" );
        int totalNumber = Integer.parseInt( totalNumberText );
        if (totalNumber > 10) {
            //定位到下一页按钮
            WebElement nextPage = driver.findElement( By.xpath( "" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            WebElement element = driver.findElement( By.xpath( "" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            Logger.getLogger( LogManagerMenu.class ).info( "外部系统调用列表数据不足10条" );
        }
    }


    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    //ca登录认证日志
    public static void accessApproveLogMenu() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到日志管理菜单
        WebElement logMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/div" ) );
        actions.moveToElement( logMenu ).click( logMenu ).perform();
        //定位到ca登录认证日志菜单
        WebElement accessApproveLog = driver.findElement( By.xpath( "" ) );
        actions.moveToElement( accessApproveLog ).click( accessApproveLog ).perform();
        // *************************查询功能


        //************************翻页功能
        String totalNumberText = driver.findElement( By.xpath( "" ) ).getText().replaceAll( "[^0-9]", "" );
        int totalNumber = Integer.parseInt( totalNumberText );
        if (totalNumber > 10) {
            //定位到下一页按钮
            WebElement nextPage = driver.findElement( By.xpath( "" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            WebElement element = driver.findElement( By.xpath( "" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            Logger.getLogger( LogManagerMenu.class ).info( "ca登录认证日志列表数据不足10条" );
        }
    }
}
