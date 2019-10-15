package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UKeyElecSealRelationPage extends TdrManagerBasic {

    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public static void uKeyElecSealRelation() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到共享盾管理菜单
        WebElement uKeyManagerMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[5]/div" ) );
        actions.moveToElement( uKeyManagerMenu ).click( uKeyManagerMenu ).perform();
        //定位到共享盾-签章关系菜单
        WebElement uKeyElecSealRelationMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[5]/ul/li[2]/span" ) );
        actions.moveToElement( uKeyElecSealRelationMenu ).click( uKeyElecSealRelationMenu ).perform();

        // ***************查询功能
        String orgName = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();
        String orgCode = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[4]/div" ) ).getText();
        String deviceNumber = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[5]/div" ) ).getText();

        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( orgName );
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/form/div[2]/div/div/input" ) ).sendKeys( orgCode );
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/form/div[3]/div/div/input" ) ).sendKeys( deviceNumber );
        WebElement status = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/form/div[4]/div/div/div[1]/input" ) );
        actions.moveToElement( status ).click( status ).perform();
        WebElement statusValue = driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[2]/span" ) );
        actions.moveToElement( statusValue ).click( statusValue ).perform();

        //定位到查询按钮
        WebElement searchButton = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/form/div[6]/div/button[1]/span" ) );
        actions.moveToElement( searchButton ).click( searchButton ).perform();

        String assertOrgName = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();
        Assert.assertEquals( assertOrgName, orgName );

        // ***********翻页功能
        String totalNumberText=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[2]/span[1]" ) ).getText().replaceAll( "[^0-9]","" );
        int totalNumber=Integer.parseInt( totalNumberText );
        if (totalNumber>10){
            //定位到下一页按钮
            WebElement nextPage=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[2]/button[2]" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            WebElement element =driver.findElement( By.xpath( "//*[@id=\"pane-ukey-elecsealrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div") );
            String text = element.getText();
            Assert.assertNotNull( text );
        }else {
            Logger.getLogger( UKeyElecSealRelationPage.class ).info( "共享盾-签章关系列表数据不足10条" );
        }
    }
}
