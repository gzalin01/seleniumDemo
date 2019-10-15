package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageLogMenuPage extends TdrManagerBasic {

    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public static void messageLogMenu() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到消息管理菜单
        driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/div/span" ) ).click();
        //定位到短信发送记录菜单
        driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[8]/ul/li/span" ) ).click();

        // ************查询功能
        String phoneNumber=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( phoneNumber );
        WebElement messageType=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[2]/div/div/div[1]/input" ) );
        actions.moveToElement( messageType ).click( messageType ).perform();
        WebElement messageTypeValue=driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[1]" ) );
        actions.moveToElement( messageTypeValue ).click( messageTypeValue ).perform();

        WebElement sendStatus=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[3]/div/div/div[1]/input" ) );
        actions.moveToElement( sendStatus ).click( sendStatus ).perform();
        WebElement sendSuccessful=driver.findElement( By.xpath( "/html/body/div[4]/div[1]/div[1]/ul/li[1]/span" ) );
        actions.moveToElement( sendSuccessful ).click( sendSuccessful ).perform();
        //定位到查询按钮
        WebElement searchButton=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[5]/div/button[1]" ) );
        actions.moveToElement( searchButton ).click( searchButton ).perform();

        //断言验证查询正确性
        String assertPhoneNumber=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        Assert.assertEquals( assertPhoneNumber,phoneNumber );

        //定位到重置按钮
        WebElement resetButton=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[5]/div/button[2]" ) );
        actions.moveToElement( resetButton ).click( resetButton ).perform();

        //定位到测试发送一条短信
        WebElement sendMessageButton=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/form/div[5]/div/button[3]/span" ) );
        //actions.moveToElement( sendMessageButton ).click( sendMessageButton ).perform();

        //定位到详情按钮
        WebElement detail=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[10]/div/button/span" ) );
        actions.moveToElement( detail ).click( detail ).perform();
        try {
            Thread.sleep( 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement receivePhoneNumber=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[3]/div/div[2]/form/div[1]/div/div/input" ) );
        String receivePhoneNumberValue=receivePhoneNumber.getText();
        Assert.assertNotNull( receivePhoneNumberValue );

        //关闭详情弹框
        WebElement closeButton=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[3]/div/div[1]/button/i" ) );
        actions.moveToElement( closeButton ).click( closeButton ).perform();

        // ***************翻页
        String totalNumberText=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[2]/span[1]" ) ).getText().replaceAll( "[^0-9]","" );
        int totalNumber=Integer.parseInt( totalNumberText );
        System.out.println("totalNumber:"+totalNumberText);
        if (totalNumber>10){
            //定位到下一页按钮
            WebElement nextPage=driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[2]/span[1]" ) );
            actions.moveToElement( nextPage ).click( nextPage ).perform();
            //断言验证是否跳页
            String  assertNextPagePhoneNumber =driver.findElement( By.xpath( "//*[@id=\"pane-message-msnlog\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div") ).getText();
            Assert.assertNotNull( assertNextPagePhoneNumber );
        }else {
            Logger.getLogger( MessageLogMenuPage.class ).info( "短信发送记录列表数据不足10条" );
        }

    }

}
