package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrgUserOpenCountPage extends TdrManagerBasic {

    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public static void orgUserOpenCount() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到统计管理菜单
        driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[6]/div/span" ) ).click();
        WebElement orgOpenCountMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[6]/ul/li/span" ) );
        actions.moveToElement( orgOpenCountMenu ).click( orgOpenCountMenu ).perform();

        // *************查询功能
        String orgName = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        String orgCode = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();
        String corporationIdNo = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[5]/div" ) ).getText();
        String corporationPhoneNum = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[6]/div" ) ).getText();

        driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( orgName );
        driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[2]/div/div/input" ) ).sendKeys( orgCode );
        driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[3]/div/div/input" ) ).sendKeys( corporationIdNo );
        driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[4]/div/div/input" ) ).sendKeys( corporationPhoneNum );
        //定位到实名认证银行下拉框
        WebElement authenticateBankType = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[5]/div/div/div/input" ) );
        actions.moveToElement( authenticateBankType ).click( authenticateBankType ).perform();
        WebElement bankTypeValue = driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[1]" ) );
        actions.moveToElement( bankTypeValue ).click( bankTypeValue ).perform();
        //定位到账号状态下拉框
        WebElement status = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[6]/div/div/div[1]/input" ) );
        actions.moveToElement( status ).click( status ).perform();
        WebElement statusValue = driver.findElement( By.xpath( "/html/body/div[4]/div[1]/div[1]/ul/li[1]" ) );
        actions.moveToElement( statusValue ).click( statusValue ).perform();

        //定位到查询按钮
        WebElement searchButton = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[8]/div/button[1]" ) );
        actions.moveToElement( searchButton ).click( searchButton ).perform();

        //断言检查结果正确性
        String assertOrgName = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        Assert.assertEquals( orgName, assertOrgName );

        //重置按钮点击检查
        WebElement resetButton=driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[8]/div/button[2]" ) );
        actions.moveToElement( resetButton ).click( resetButton ).perform();

        //导出按钮点击检查
        WebElement exportedButton = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/form/div[8]/div/button[3]" ) );
        actions.moveToElement( exportedButton ).click( exportedButton ).perform();

        //  ****************翻页
        String totalNumberText = driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[3]/span[1]" ) ).getText().replaceAll( "[^0-9]", "" );
        int totalNumber = Integer.parseInt( totalNumberText );
        if (totalNumber > 10) {
            //定位到下一页按钮
            WebElement nextPageButton=driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[3]/button[2]/i" ) );
            actions.moveToElement( nextPageButton ).click( nextPageButton ).perform();
            //断言检查结果正确性
            String  assertNextPageOrgName=driver.findElement( By.xpath( "//*[@id=\"pane-statistical-orguseropen\"]/div/div/div/div[2]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
            Assert.assertNotEquals( orgName,assertNextPageOrgName );

        }else {
            Logger.getLogger( OrgUserOpenCountPage.class ).info( "企业开户信息列表数据不足10条" );
        }


    }

}
