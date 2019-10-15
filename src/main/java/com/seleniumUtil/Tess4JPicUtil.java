package com.seleniumUtil;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Tess4JPicUtil {
    public byte[] takeScreenshot(WebDriver driver) throws IOException {
        byte[] screenshot = null;
        screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);//得到截图
        return screenshot;
    }

    private static void takeScreenshot(WebDriver driver, String screenPath) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        } catch (IOException e) {
            System.out.println("Screen shot error: " + screenPath);
        }
    }


    public void takeScreenshot2(WebDriver driver) {
        String screenName = String.valueOf(new Date().getTime()) + ".jpg";
        File dir = new File("test-output/snapshot");
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "/" + screenName;
        this.takeScreenshot(driver, screenPath);
    }

    public BufferedImage createElementImage(WebDriver driver, WebElement webElement, int x, int y, int width, int heigth)//开始裁剪的位置和截图的宽和高
            throws IOException {
        Dimension size = webElement.getSize();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(
                takeScreenshot(driver)));
        BufferedImage croppedImage = originalImage.getSubimage(x, y,
                size.getWidth() + width, size.getHeight() + heigth);//进行裁剪
        return croppedImage;
    }

}
