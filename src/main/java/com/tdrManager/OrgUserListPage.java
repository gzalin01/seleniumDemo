package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrgUserListPage extends TdrManagerBasic {
    private static final Logger logger = Logger.getLogger( OrgUserListPage.class );

    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public void orgUserList() throws InterruptedException {
        CustomerSignaturePage.tdrManagerBasic();

        //定位到企业管理菜单
        WebElement orgManagerMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[2]/div" ) );
        actions.moveToElement( orgManagerMenu ).click( orgManagerMenu ).perform();
        //定位到企业信息管理菜单
        WebElement orgManagerInfoMenu = orgManagerMenu.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[2]/ul/li[1]" ) );
        actions.moveToElement( orgManagerInfoMenu ).click( orgManagerInfoMenu ).perform();
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 开启开发者模式，方便观察请求
//        Actions builder = new Actions( driver );
//        builder.sendKeys( Keys.F12 ).perform();


        //  *******************查询功能

        //定位到公司名称输入框
        WebElement orgUserNameElement = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/form/div[1]/div/div/input" ) );
        //输入查询条件
        orgUserNameElement.clear();
        orgUserNameElement.sendKeys( "天地融科技" );
        //定位到社会统一信用代码输入框
        WebElement orgUserCode = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/form/div[2]/div/div/input" ) );
        orgUserCode.clear();
        orgUserCode.sendKeys( "91440101550557719G" );

        //定位到查询按钮输入框
        WebElement checkButton = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/form/div[8]/div/button[1]/span" ) );
        //点击查询按钮
        checkButton.click();

        //设置等待
        Thread.sleep( 2000 );

        //断言检查结果正确性
        WebElement orgCode = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/div[1]/div[3]/table/tbody/tr/td[4]/div" ) );
        String orgCodeObject = orgCode.getText();
        Assert.assertEquals( "91440101550557719G", orgCodeObject );


        //定位到重置按钮刷新页面验证锁定账号和解锁账号功能
        WebElement reset = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/form/div[8]/div/button[2]/span" ) );
        reset.click();
        Thread.sleep( 1000 );


        // ****************进行【锁定账号】和【解除锁定】操作
        Actions action = new Actions( driver );
        WebElement table = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/div[1]/div[4]/div[2]/table" ) );
        WebElement tbody = table.findElement( By.tagName( "tbody" ) );
        List<WebElement> trs = tbody.findElements( By.tagName( "tr" ) );

        boolean isApplyLock = Boolean.FALSE;
        boolean isApplyUnLock = Boolean.FALSE;

        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements( By.tagName( "td" ) );
            int size = tds.size() - 1;
            //定位到最后一行即操作栏
            WebElement div = tds.get( size ).findElement( By.tagName( "div" ) );
            //定位到【锁定账号】按钮
            WebElement lockButton = div.findElement( By.tagName( "button" ) );
            driver.manage().timeouts().implicitlyWait( 5, TimeUnit.SECONDS );
            String buttonText = lockButton.findElement( By.tagName( "span" ) ).getText();
            System.out.println( "buttonText:" + buttonText );

            if (buttonText.equals( "锁定账号" ) && !isApplyLock) {
                isApplyLock = Boolean.TRUE;
                //点击【锁定账户】按钮
                action.moveToElement( lockButton ).click( lockButton ).perform();
                //定位到提示框中的【确定】
                WebElement messageButton = driver.findElement( By.cssSelector( "button.el-button.el-button--default.el-button--small.el-button--primary" ) );
                System.out.println( "提示框操作按钮文本:" + messageButton.findElement( By.cssSelector( "span" ) ).getText() );
                //点击【确定】按钮
                action.clickAndHold( messageButton ).perform();
                //释放鼠标
                action.release().perform();

            } else if (buttonText.equals( "解除锁定" ) && !isApplyUnLock) {
                isApplyUnLock = Boolean.TRUE;
                //点击【解锁账户】按钮
                action.moveToElement( lockButton ).click( lockButton ).perform();
                //定位到提示框中的【确定】
                WebElement messageButton2 = driver.findElement( By.cssSelector( "button.el-button.el-button--default.el-button--small.el-button--primary" ) );
                System.out.println( "提示框操作按钮文本:" + messageButton2.findElement( By.cssSelector( "span" ) ).getText() );
                //点击【确定】按钮
                action.moveToElement( messageButton2 ).click( messageButton2 ).perform();
                //释放鼠标
                action.release().perform();
            }

            if (isApplyLock && isApplyUnLock) {
                break;
            }

        }


        //  *******************进行翻页操作
        //定位到下一页按钮
        WebElement nextPage = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/div[2]/button[2]" ) );
        String totalNumberText = driver.findElement( By.cssSelector( "span.el-pagination__total" ) ).getText().replaceAll( "[^0-9]","" );
        int totalNumber = Integer.parseInt(totalNumberText );
        System.out.println( "totalNumberText:" + totalNumberText );
        if (totalNumber > 10) {
            nextPage.click();
            WebElement element = driver.findElement( By.xpath( "//*[@id=\"pane-customer-orguser\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            logger.info( "企业信息管理列表数据不足10条" );
        }

    }


}
