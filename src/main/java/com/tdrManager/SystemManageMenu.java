package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SystemManageMenu extends TdrManagerBasic {


    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    //角色管理
    public static void roleManager() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到系统管理菜单
        WebElement systemManagerMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/div" ) );
        actions.moveToElement( systemManagerMenu ).click( systemManagerMenu ).perform();
        //定位到角色管理菜单
        WebElement roleManagerMenu = driver.findElement( By.xpath( "" ) );
        actions.moveToElement( roleManagerMenu ).click( roleManagerMenu ).perform();
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
            Logger.getLogger( LogManagerMenu.class ).info( "角色管理列表数据不足10条" );
        }
    }
}
