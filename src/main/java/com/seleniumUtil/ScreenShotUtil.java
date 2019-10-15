package com.seleniumUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

public class ScreenShotUtil {
    public WebDriver driver;

    public ScreenShotUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**截图并存储到screenPath
     *
     * @param driver
     * @param screenPath
     */
    private static void takeScreenshot(WebDriver driver, String screenPath) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        } catch (IOException e) {
            System.out.println("Screen shot error: " + screenPath);
        }
    }

    /**截图并存储到固定目录下test-output/snapshot中
     *
     * @param driver 浏览器驱动
     */
    public void takeScreenshot(WebDriver driver) {
        String screenName = String.valueOf(new Date().getTime()) + ".jpg";
        File dir = new File("test-output/snapshot");
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "/" + screenName;
        System.out.println("截图存储路径为："+screenPath);
        this.takeScreenshot(driver, screenPath);
    }

    public void takeScreenShot(){
        //获取当前时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        String currentDate=dateFormat.format(new Date());
        //获取当前类名
        String currentClassName=this.getClass().getName();
        //路径
        String picPath=currentClassName+"_"+currentDate+"png";
        //String currentPath=System.getProperty("")
    }

}
