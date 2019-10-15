package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomerAuthFilePage extends TdrManagerBasic {

    @Test(dependsOnGroups = {"loginSuccessful"}, description = "登录成功才执行")
    public static void customerAuthFile() {
        CustomerAuthFilePage.tdrManagerBasic();
        //定位到授权签章菜单
        WebElement authMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[4]/div" ) );
        actions.moveToElement( authMenu ).click( authMenu ).perform();
        //定位到授权审核菜单
        WebElement authAutidMenu = authMenu.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[4]/ul/li[1]" ) );
        actions.moveToElement( authAutidMenu ).click( authAutidMenu ).perform();
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // **************获取查询参数并传到查询功能中
        String orgCompanyName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) ).getText();
        String authUserName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[6]/div" ) ).getText();
        String authUserCode = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[5]/div" ) ).getText();
        String authUserTel = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[7]/div" ) ).getText();

        // **************查询功能
        //定位到企业名称
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[1]/div/div/input" ) ).sendKeys( orgCompanyName );
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[2]/div/div/input" ) ).sendKeys( authUserName );
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[3]/div/div/input" ) ).sendKeys( authUserCode );
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[4]/div/div/input" ) ).sendKeys( authUserTel );


        //定位到审核状态
        WebElement applyStatus = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[5]/div/div/div/span/span/i" ) );
        //((JavascriptExecutor) driver).executeScript( "document.getElementsByClassName('el-button el-button--warning el-button--small is-plain')[0].click();",applyStatus);
        actions.moveToElement( applyStatus ).click( applyStatus ).perform();

        //点击选择待审核状态
        //((JavascriptExecutor) driver).executeScript( "document.getElementsByClassName('el-select-dropdown__item hover')[0].click();");
        driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[1]/span" ) ).click();
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].click();", applyPass);


        /*
        //定位到开始时间时间控件
        WebElement startTime=driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[6]/div/div/i[1]" ) );
        actions.moveToElement( startTime ).click( startTime ).perform();

        //定位到开始时间年月(此处定位到18年当前月)
        WebElement startMonth=driver.findElement(By.cssSelector( "button.el-picker-panel__icon-btn.el-icon-d-arrow-left") );
        actions.moveToElement( startMonth ).click( startMonth ).perform();
        //定位到第一个表格，开始时间日(此处定位到第一个周日)
        driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div/div[2]/table/tbody/tr[4]/td[1]/div/span" ) ).click();
        //actions.moveToElement( startDay ).click( startDay ).perform();

        //定位到结束时间日
        WebElement endTime=driver.findElement( By.xpath( "/html/body/div[6]/div[1]/div/div[3]/table/tbody/tr[5]/td[5]/div/span" ) );
        actions.moveToElement( endTime ).click( endTime ).perform();
        //定位到确定按钮
        driver.findElement( By.xpath( "/html/body/div[6]/div[2]/button[2]" ) ).click();

         */


        //定位到查询按钮
        WebElement checkButton = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[7]/div/button[1]/span" ) );
        actions.moveToElement( checkButton ).click( checkButton ).perform();

        //断言检查是否为空
        String orgName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr/td[2]/div" ) ).getText();
        Assert.assertNotNull( orgName );

        //定位到【重置】按钮
        WebElement reset = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/form/div[7]/div/button[2]" ) );
        System.out.println( "reset:" + reset.getText() );
        actions.moveToElement( reset ).click( reset ).perform();


        // ***********【详情】按钮
        WebElement authDetail = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[10]/div/button[1]/span" ) );
        actions.moveToElement( authDetail ).click( authDetail ).perform();
        //((JavascriptExecutor) driver).executeScript( "arguments[0].click();", anthDetail );
        //JavascriptExecutor js=((JavascriptExecutor) driver);
        //js.executeScript("document.getElementsByClassName('el-button el-button--warning el-button--small is-plain')[0].click();");
        try {
            Thread.sleep( 5000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement detailOrgCompanyName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[10]/div/button[1]" ) );
        //断言详情页面中公司名称不为空
        Assert.assertNotNull( detailOrgCompanyName );
        driver.navigate().refresh();


        // ***********【审核】按钮
        WebElement audit = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[4]/div[2]/table/tbody/tr[1]/td[10]/div/button[2]/span" ) );
        //actions.moveToElement( audit ).click( audit ).perform();
        //WebElement auditDetail = wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[3]/div[1]/div[2]/form/div[1]/label" ) ) );


        // ****翻页功能
        //获取页面列表数量
        WebElement totalNumber = driver.findElement( By.cssSelector( "span.el-pagination__total" ) );
        int totalNumberText = Integer.parseInt( totalNumber.getText().replaceAll( "[^0-9]", "" ) );
        System.out.println( "totalNumberText:" + totalNumber.getText() );
        if (totalNumberText > 10) {
            WebElement nextPage = driver.findElement( By.cssSelector( "button.btn-next" ) );
            nextPage.click();
            WebElement element = driver.findElement( By.xpath( "//*[@id=\"pane-customer-authfile\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            Logger.getLogger( CustomerAuthFilePage.class ).info( "授权审核菜单列表数据不足10条" );
        }


    }
}

