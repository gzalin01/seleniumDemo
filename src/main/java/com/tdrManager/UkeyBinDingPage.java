package com.tdrManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UkeyBinDingPage extends TdrManagerBasic {
    private static final Logger logger = Logger.getLogger( UkeyBinDingPage.class );

    @Test(dependsOnGroups = {"loginSuccessful"},description = "登录成功才执行")
    public void ukeyBinDing() {
        CustomerSignaturePage.tdrManagerBasic();
        //定位到证书管理菜单
        WebElement uKeyMenu = driver.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[3]/div/span" ) );
        actions.moveToElement( uKeyMenu ).click( uKeyMenu ).perform();
        //定位到授权审核菜单
        WebElement uKeyBanDingMenu = uKeyMenu.findElement( By.xpath( "/html/body/div[1]/aside/div/ul/li[3]/ul/li" ) );
        actions.moveToElement( uKeyBanDingMenu ).click( uKeyBanDingMenu ).perform();
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // **************查询功能
        //定位到银行类型下拉框
        WebElement bankType = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/form/div[1]/div/div/div/input" ) );
        actions.moveToElement( bankType ).click( bankType ).perform();

        //定位到银行类型下拉框值--工商银行
        WebElement bankTypeValue = driver.findElement( By.xpath( "/html/body/div[3]/div[1]/div[1]/ul/li[1]" ) );
        actions.moveToElement( bankTypeValue ).click( bankTypeValue ).perform();

        //定位到绑定状态下拉框
        WebElement banDingStatus = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/form/div[2]/div/div/div/input" ) );
        actions.moveToElement( banDingStatus ).click( banDingStatus ).perform();

        //定位到绑定成功
        driver.findElement( By.xpath( "/html/body/div[4]/div[1]/div[1]/ul/li[1]" ) ).click();

        //定位到开始时间时间控件
        WebElement startTime = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/form/div[3]/div/div/i[1]" ) );
        actions.moveToElement( startTime ).click( startTime ).perform();
        driver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );

        //定位到开始时间年月(此处定位到18年当前月)
        WebElement startMonth = driver.findElement( By.cssSelector( "button.el-picker-panel__icon-btn.el-icon-d-arrow-left" ) );
        actions.moveToElement( startMonth ).click( startMonth ).perform();

        //定位到第一个表格，开始时间日(此处定位到第一个周日)
        driver.findElement( By.xpath( "/html/body/div[5]/div[1]/div/div[2]/table/tbody/tr[3]/td[1]/div/span" ) ).click();

        //定位到结束时间日
        WebElement endTime = driver.findElement( By.xpath( "/html/body/div[5]/div[1]/div/div[3]/table/tbody/tr[2]/td[2]/div/span" ) );
        actions.moveToElement( endTime ).click( endTime ).perform();
        //定位到确定按钮
        driver.findElement( By.xpath( "/html/body/div[5]/div[2]/button[2]" ) ).click();

        //定位到查询按钮
        driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/form/div[4]/div/button[1]" ) ).click();

        //断言检查是否为空
        WebElement orgName = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) );
        Assert.assertNotNull( orgName );

        //点击重置按钮
        WebElement resert = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/form/div[4]/div/button[2]" ) );
        actions.moveToElement( resert ).click( resert ).perform();

        // *********存在绑定失败的数据进行重新绑定
        WebElement operate;
        WebElement table = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/div[1]/div[3]/table" ) );
        List<WebElement> trs = table.findElements( By.tagName( "tr" ) );
        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements( By.tagName( "td" ) );
            int tdSize = tds.size() - 1;
            operate = tds.get( tdSize );
            if (operate != null) {
                logger.info( "证书绑定交易中心列表中存在绑定失败的记录，可进行重绑" );
            } else {
                logger.info( "证书绑定交易中心列表中没有绑定失败的记录" );
            }
        }

        // ***************翻页检查
        //定位到下一页按钮
        String totalNumberText = driver.findElement( By.cssSelector( "span.el-pagination__total" ) ).getText().replaceAll( "[^0-9]","" );
        int totalNumber = Integer.parseInt( totalNumberText );
        System.out.println( "totalNumber:" +totalNumber );
        if (totalNumber > 10) {
            WebElement nextPage = driver.findElement( By.cssSelector( "button.btn-next" ) );
            nextPage.click();
            WebElement element = driver.findElement( By.xpath( "//*[@id=\"pane-customer-ukeybinding\"]/div/div/div/div[1]/div[3]/table/tbody/tr[1]/td[2]/div" ) );
            String text = element.getText();
            Assert.assertNotNull( text );
        } else {
            logger.info( "证书绑定交易中心列表数据不足10条" );
        }

    }

}
