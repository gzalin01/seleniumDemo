package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UKeyCertRelationPage extends TdrManagerBasic {
    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public static void uKeyCertRelation(){
        CustomerSignaturePage.tdrManagerBasic();
        //定位到共享盾管理菜单
        WebElement uKeyManagerMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[5]/div" ) );
        actions.moveToElement( uKeyManagerMenu ).click( uKeyManagerMenu ).perform();
        //定位到企业信息管理菜单
        WebElement uKeyCertRelationMenu = uKeyManagerMenu.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[5]/ul/li[1]" ) );
        actions.moveToElement( uKeyCertRelationMenu ).click( uKeyCertRelationMenu ).perform();
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // **********查询功能
        String orgCompanyName = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();
        String deviceNum = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[5]/div" ) ).getText();
        String authUserCode = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[4]/div" ) ).getText();

        //定位到企业名称
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( orgCompanyName );
        //定位社会统一信用代码
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[2]/div/div/input" ) ).sendKeys( authUserCode );
        //定位到U盾序列号
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[3]/div/div/input" ) ).sendKeys( deviceNum );
        //选择认证类型
        WebElement ukeyType=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[4]/div/div/div/input" ) );
        ukeyType.click();
        WebElement uKeyTypeValue=driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[2]" ) );
        actions.moveToElement( uKeyTypeValue ).click( uKeyTypeValue ).perform();
        //选择证书状态
        WebElement certStatus=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[5]/div/div/div[1]/input" ) );
        certStatus.click();
        WebElement certStatusValue=driver.findElement( By.xpath( "/html/body/div[4]/div[1]/div[1]/ul/li[2]" ) );
        actions.moveToElement( certStatusValue ).click( certStatusValue ).perform();
        //选择盾状态
        driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[6]/div/div/div[1]/input" ) ).click();
        WebElement uKeyStatus=driver.findElement( By.xpath( "/html/body/div[5]/div[1]/div[1]/ul/li[1]" ) );
        actions.moveToElement( uKeyStatus ).click( uKeyStatus ).perform();
        //定位到查询按钮
        WebElement search=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/form/div[8]/div/button[1]" ) );
        actions.moveToElement( search ).click( search ).perform();
        String assertOrgCompanyName = driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();
        Assert.assertEquals( assertOrgCompanyName,orgCompanyName );

        //定位到删除按钮
        WebElement delete=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[19]/div/button" ) );
        //actions.moveToElement( delete ).click( delete ).perform();

        // *******翻页功能检查
        //获取列表数量
        String totalNumberText=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[2]/span[1]" ) ).getText().replaceAll( "[^0-9]","" );
        int totalNumber=Integer.parseInt( totalNumberText );
        if (totalNumber>10){
            //定位到下一页按钮
            WebElement nextPage=driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[2]/button[2]/i" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            WebElement assertNextPageOrgName =driver.findElement( By.xpath( "//*[@id=\"pane-ukey-certrelation\"]/div/div/div/div[1]/div[3]/table/tbody/tr/td[3]/div") );
            String text = assertNextPageOrgName.getText();
            Assert.assertNotNull( text );
        }else {
            Logger.getLogger( UKeyCertRelationPage.class ).info( "共享盾-证书关系列表数据不足10条" );
        }














    }

}