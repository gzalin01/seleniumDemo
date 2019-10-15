package com.tdrManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class CustomerSignaturePage extends TdrManagerBasic {

    @Test(dependsOnGroups = {"loginSuccessful"})
    public static void customerSignature() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到签章授权菜单
        WebElement authMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[4]/div" ) );
        actions.moveToElement( authMenu ).click( authMenu ).perform();
        //定位到签章制作菜单
        WebElement signature = authMenu.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[4]/ul/li[2]/span" ) );
        actions.moveToElement( signature ).click( signature ).perform();

        // *******查询功能
        //定位到列表中展示信息并传入查询条件
        String orgName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        String fileName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[3]/div" ) ).getText();

        driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( orgName );
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/form/div[2]/div/div/input" ) ).sendKeys( fileName );

        //定位到查询按钮
        WebElement seacher = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/form/div[4]/div/button[1]" ) );
        actions.moveToElement( seacher ).click( seacher ).perform();

        //断言检查结果正确性
        String orgNameAsser = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        Assert.assertNotNull( orgNameAsser );

        //定位到【重置】按钮
        WebElement reset = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/form/div[4]/div/button[2]" ) );
        actions.moveToElement( reset ).click( reset ).perform();

        // ***********详情按钮
        //定位到【详情】按钮,点击详情按钮
        WebElement signatureDetail = (new WebDriverWait( driver, 2 )).until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[6]/div/button" ) ) );
        actions.moveToElement( signatureDetail ).click( signatureDetail ).perform();
        //断言检查结果正确性
        WebElement assertElement = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[3]/div[1]/div[2]/form/div[1]/div/div/input" ) );
        Assert.assertNotNull( assertElement );
        //定位详情按钮的关闭
        //WebElement detailClose=(new WebDriverWait( driver,10)).until( ExpectedConditions.presenceOfElementLocated( By.xpath( "/html/body/div[1]/div/main/div/div[2]/div[3]/div/div/div/div[3]/div[1]/div[1]/button" ) ) );
        //actions.moveToElement( detailClose ).click( detailClose ).perform();
        driver.navigate().refresh();

        // **************制作签章
        //定位到操作栏所有操作按钮
        List<WebElement> signatureButtons = driver.findElements( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr/td/div/button/span" ) );
        Boolean flag = Boolean.TRUE;
        for (WebElement signatureButton : signatureButtons) {
            //定位到【制作签章】按钮
            if (signatureButton.getText().equals( "制作签章" ) && flag) {
                System.out.println( "signatureButton:" + signatureButton.getText() );
                flag = Boolean.FALSE;
                actions.moveToElement( signatureButton ).click( signatureButton ).perform();
                try {
                    Thread.sleep( 5000 );
                    //获取当前窗口句柄
                    String currentWindowHandle = driver.getWindowHandle();
                    //页面发生跳转，需要切换窗口
                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (handle.equals( currentWindowHandle ))
                            continue;
                        driver.switchTo().window( handle );
                    }
                    System.out.println( "当前页面title：" + driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[3]/div/div[1]/span" ) ).getText() );
                    //定位到【制作签章】按钮
                    WebElement doSignatureReal = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[3]/div/div[2]/div/div[2]/div[1]/div/div[1]/button" ) );
                    System.out.println( "正在操作按钮：" + doSignatureReal.getText() );
                    actions.moveToElement( doSignatureReal ).click( doSignatureReal ).perform();
                    Thread.sleep( 1000 );
                    //关闭当前页面返回至制作签章列表菜单
                    WebElement close = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[3]/div/div[1]/button" ) );
                    close.click();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Logger.getLogger( "制作签章列表中没有待制作状态的数据" );
            }
        }


        // ************翻页功能
        //获取页面列表数量
        String totalNumberText = driver.findElement( By.cssSelector( "span.el-pagination__total" ) ).getText().replaceAll( "[^0-9]", "" );
        System.out.println( "totalNumberText:" + totalNumberText );
        int totalNumber = Integer.parseInt( totalNumberText );
        if (totalNumber > 10) {
            WebElement nextPage = driver.findElement( By.cssSelector( "button.btn-next" ) );
            nextPage.click();
            WebElement element = driver.findElement( By.xpath( "//*[@id=\"pane-customer-signature\"]/div/div/div/div[2]/button[2]" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            org.apache.log4j.Logger.getLogger( CustomerAuthFilePage.class ).info( "授权审核菜单列表数据不足10条" );
        }


    }
}
