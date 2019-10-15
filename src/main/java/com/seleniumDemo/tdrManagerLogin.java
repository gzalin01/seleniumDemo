package com.seleniumDemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class tdrManagerLogin {
    WebDriver driver;
    String loginUrl="http://123.124.21.115:8081/portal/index.html";
    private static final Logger logger = Logger.getLogger(tdrManagerLogin.class);
    //高精度
    private static  String ocrUrl ="https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
    //通用版
    // private static  String ocrUrl ="https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    //网络文字版
    // private static  String ocrUrl="https://aip.baidubce.com/rest/2.0/ocr/v1/webimage";


    /*
    //测试简单的验证码识别（注意图片格式）
    @Test
    public void simpleTess4j(){
        File imageFile = new File("C:\\Users\\tdr_lhy\\Desktop\\1278.jpg");
        ITesseract instance = new Tesseract();
        File tess=LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tess.getPath());
        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

     */


    @Test
    public void login() throws IOException, InterruptedException {
        //窗口最大化
        driver.manage().window().maximize();

        driver.get(loginUrl);

        //获取当前窗口URL
        System.out.println("登陆前URL："+driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement account=driver.findElement( By.xpath("/html/body/div/div/div/div[2]/form/div[1]/div/div/input"));
        account.clear();
        account.sendKeys("admin");
        WebElement password=driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[2]/div/div/input"));
        password.clear();
        password.sendKeys("admin");
        WebElement captcha=driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[3]/div/div/div[1]/div/input"));
        captcha.clear();
        //获取验证码截图
        getVerificationImage();
        //png图片转为jpg格式
        pngImageCoverTojpgImage();
        String codeUrl="D:\\截取的图片.jpg";
        //降噪，去干扰线识别率更低，尴尬故此禁用
//        File sFile=new File(codeUrl);
//        ImageProcessing(sFile,"D:\\");
        //cleanLinesInImage(codeUrl,"D:\\");
        String code= getVerificationCode(codeUrl).replaceAll("[^0-9a-zA-Z]", "");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("code:"+code);
        captcha.sendKeys(code);
        WebElement loginButton=driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[4]/div/button/span"));
        loginButton.click();
        //获取当前窗口的句柄
        String currentWindowHandle=driver.getWindowHandle();
        System.out.println("currentWindowHandle="+currentWindowHandle);
        Set<String> handles=driver.getWindowHandles();
        for(String handle:handles){
            if (handle.equals( currentWindowHandle )) {
                continue;
            }
                driver.switchTo().window( handle );
        }

        //注意页面跳转一定要增加这种等待时间
        Thread.sleep(5000);

        //登录成功后跳转的url
        String loginSuccessUrl="http://123.124.21.115:8081/portal/index.html#/home";
        String currentUrl=driver.getCurrentUrl();
        if (currentUrl.equals( loginSuccessUrl )){
            logger.info(">>>>>>登录账号:admin，密码admin：登录成功");
        }else {
            //捕获登录失败信息
            String error=driver.findElement(By.xpath( "/html/body/div/div/div/div[2]/form/div[3]/div/div[2]" )).getText();
            logger.info(">>>>>>登录:账号admin，密码admin。-----结果："+error);
        }
        //定位到企业管理菜单并点击
        driver.findElement(By.xpath("/html/body/div[1]/aside/div/ul/li[2]/div/span")).click();
        //定位到
        WebElement orgUserElement= driver.findElement(By.xpath("/html/body/div[1]/aside/div/ul/li[2]/ul/li/span"));

        System.out.println("orgUserElement:"+orgUserElement.getText());





    }

    //获取验证码截图并保存到D盘
    private void  getVerificationImage() throws IOException {

        //获取整个屏幕截图
        File screenshot =((TakesScreenshot)driver).getScreenshotAs( OutputType.FILE);
        BufferedImage fullImg= ImageIO.read( screenshot );
        File aaFile = new File( "D:\\整个屏幕截图.png" );
        FileUtils.copyFile(screenshot, aaFile );
        //BufferedImage read = ImageIO.read(aaFile);
        //BufferedImage eleScreenshot= read.getSubimage(100,100,200,50);

        //定位验证码横纵坐标和长宽
        WebElement element=driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[3]/div/div/div[2]/img"));
        int pointX=element.getLocation().getX();
        int pointY=element.getLocation().getY();
        System.out.println("pointX:"+pointX);
        System.out.println("pointY:"+pointY);
        int width=element.getSize().getWidth();
        int height=element.getSize().getHeight();
        System.out.println("width:"+width);
        System.out.println("height:"+height);

        //将验证码截图(由于受chrome正受自动化控制浮窗的影响，元素坐标定位不准确)
        //BufferedImage eleScreenshot= fullImg.getSubimage(pointX,pointY,width,height);
        BufferedImage eleScreenshot= fullImg.getSubimage(1675,399,198,50);
        File output = new File( "D:\\截取的图片.png" );
        ImageIO.write( eleScreenshot, "png", output );
        //对验证码图片进行降噪，去除干扰线处理
        // ImageIoHelper ioHelper= new ImageIoHelper();
        // File file= ioHelper.createImage( output,"png" );
//        ImageUtil imageUtil=new ImageUtil();
//        imageUtil.cleanLinesInImage(output,"D:\\ ");

//        File  file= new File("D:\\降噪后的图片.png ");

          /*
        //调用Tesseract读取处理后的验证码图片
        ITesseract instance = new Tesseract();
        File tess=LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tess.getPath());//进行读取，默认是英文，如果要使用中文包，加上instance.setLanguage("chi_sim"); 
        String result=null;
        try {
            result = instance.doOCR(output);
        } catch (TesseractException e1) {
            e1.printStackTrace();
        }
        result = result.replaceAll("[^a-z^A-Z^0-9]", "");//替换大小写及数字
        System.out.println("图形验证码为"+result);
           */

    }

    //获取验证码
    public static String getVerificationCode(String imageUrl)  {
        //获取accessToken
        String accessToken=getAccessToken();
        String verificationCode = "";
        String targetUrl = ocrUrl + "?access_token=" + accessToken;
        System.out.println("targetUrl:"+targetUrl);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> params = new HashMap<>();
        imageUrl = encodeImgeToBase64(imageUrl);
        params.put("image", imageUrl);

        HttpRequestData httpRequestData = new HttpRequestData();
        httpRequestData.setHeaders(headers);
        httpRequestData.setRequestMethod("post");
        httpRequestData.setParams(params);
        httpRequestData.setRequestUrl(targetUrl);
        HttpResponse response = HttpClientUtils.execute(httpRequestData);

        String json = "";
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                // ReadContext readContext = JsonPath.parse( response );
                json = IOUtils.toString(response.getEntity().getContent());
                JSONObject jsonObject = JSONObject.parseObject(json);
                JSONArray wordsResult = jsonObject.getJSONArray("words_result");
                verificationCode = wordsResult.getJSONObject(0).getString("words");
                //过滤除数字，字母之外的字符
            } catch (IOException e) {
                logger.error("请求识别失败！", e);
            }
        }
        return verificationCode;

    }

    //获取accessToken
    public  static String getAccessToken() {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        //百度获取accessToken地址
        String url = "https://aip.baidubce.com/oauth/2.0/token";
        String accessToken = "";
        try {
            HttpPost post = new HttpPost( url );
            //创建参数列表
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add( new BasicNameValuePair( "grant_type", "client_credentials" ) );
            list.add( new BasicNameValuePair( "client_id", "lFOZFfEoYhtp8abwawgs865d" ) );
            list.add( new BasicNameValuePair( "client_secret", "rhRI1pp3GIpYefLGXTI30gn5o4h6YUqg" ) );

            //url格式编码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity( list, "UTF-8" );
            post.setEntity( uefEntity );

            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute( post );
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString( entity );
            // 得到响应内容
            httpResponse.close();
            System.out.println( "responseBody:" + responseBody );
            ReadContext readContext = JsonPath.parse( responseBody );
            accessToken = readContext.read( "$.access_token" );
            return accessToken;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    //对图片进行Base64编码处理
    public static String encodeImgeToBase64(String imagePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            File imageFile = new File(imagePath);
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Data= Base64.encodeBase64String(data);
        return base64Data;
    }

    //将png图片转为jpg
    public static void pngImageCoverTojpgImage(){
        BufferedImage bufferedImage;

        try {
            //read image file
            bufferedImage = ImageIO.read(new File("D:\\截取的图片.png"));

            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File("D:\\截取的图片.jpg"));
            System.out.println("png图片转为jpg图片完成");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @BeforeMethod
    public void startDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver=new ChromeDriver();
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }
}
