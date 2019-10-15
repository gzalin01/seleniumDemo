package com.tdrManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.seleniumDemo.FileUtil;
import com.seleniumDemo.HttpClientUtils;
import com.seleniumDemo.HttpRequestData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;
import static com.seleniumDemo.tdrManagerLogin.getAccessToken;

public class Login {
    public static String loginUrl = "http://123.124.21.115:8082/adminApi/admin/sys/login";
    //高精度
    private static String ocrUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
    //通用版
    // private static  String ocrUrl ="https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    //网络文字版
    // private static  String ocrUrl="https://aip.baidubce.com/rest/2.0/ocr/v1/webimage";
    private static final Logger logger = Logger.getLogger( Login.class );
    //获取uuid
    private static String uuid = UUID.randomUUID().toString();

    //定义CloseableHttpClient
    private static CloseableHttpClient closeableHttpClient;
    private static HttpResponse response;
    private static HttpPost post;
    //获取当前时间戳
    private static Long timeStamp = System.currentTimeMillis();
    private static String t = String.valueOf( timeStamp );
    private static String reqSerialId = "admin" + t;
    private static String captcha;

    @Test(groups = {"loginSuccessful"})
    public static void login() throws IOException {
        //创建一个HttpClient
        closeableHttpClient = HttpClients.createDefault();
        //获取验证码正确性

        try {
            captcha = verifyCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建一个post对象
        post = new HttpPost( loginUrl );

        //构建json参数
        JSONObject jsonObj = new JSONObject();
        jsonObj.put( "appName", "ADMIN" );
        jsonObj.put( "username", "admin" );
        jsonObj.put( "password", "admin" );
        jsonObj.put( "captcha", captcha );
        jsonObj.put( "t", timeStamp );
        jsonObj.put( "reqSerialId", reqSerialId );
        jsonObj.put( "uuid", uuid );



        //创建一个entry对象
        StringEntity entity = new StringEntity( jsonObj.toString(), "UTF-8" );

        //设置参数传输格式
        entity.setContentEncoding( "UTF-8" );
        entity.setContentType( "application/json" );
        post.setEntity( entity );


        //执行post请求
        try {
            response = closeableHttpClient.execute( post );
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过EntityUtils获取返回内容
        if (response.getStatusLine().getStatusCode() == 200) {
            String responseBody = EntityUtils.toString( response.getEntity(), "UTF-8" );
            System.out.println( "loginResponseBody:" + responseBody );
            //转换成json,根据合法性返回json或者字符串
            ReadContext readContext = JsonPath.parse( responseBody );
            String token = readContext.read( "$.token" );
            //Assert.assertNotNull( token );
            try {
                FileWriter tokenStream = new FileWriter( "D:\\ALIN\\selenium\\token.txt" );
                BufferedWriter out = new BufferedWriter( tokenStream );
                out.write( token );
                out.close();
                tokenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            logger.info( "登录失败。。。。。。。。" );
        }
    }


    //获取验证码并下载到本地
    public static void downloadCodeImage() throws IOException {
        String captchaUrl = "http://123.124.21.115:8082/adminApi/admin/captcha.jpg";
        String url = captchaUrl + "?uuid=" + uuid;
        URL serverUrl = new URL( url );
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.connect();
        // 判断请求是否成功
        if ((conn).getResponseCode() == 200) {
            InputStream in = null;
            in = conn.getInputStream();
            FileUtil.inputStreamToFile( in, "D:\\checkImg.jpg" );
        } else {
            System.out.println( "下载code图片失败！" );
        }
    }

    //验证验证码正确性
    public static String verifyCode() throws IOException {
        String verifyCodeUrl = "http://123.124.21.115:8082/adminApi/admin/sys/validateCaptcha";
        closeableHttpClient = HttpClients.createDefault();
        //创建一个post对象
        post = new HttpPost( verifyCodeUrl );

        //获取验证码并下载到本地
        downloadCodeImage();
        //百度ocr识别验证码
        String captcha = getVerificationCode( "D:\\checkImg.jpg" ).replaceAll( "[^0-9a-zA-Z]", "" );
        System.out.println( "验证码captcha:" + captcha );


        //json方式：构建json参数
        JSONObject jsonParam = new JSONObject();
        jsonParam.put( "appName", "ADMIN" );
        jsonParam.put( "captcha", captcha );
        jsonParam.put( "t", timeStamp );
        jsonParam.put( "reqSerialId", reqSerialId );
        jsonParam.put( "uuid", uuid );


        //    表单方式
        //    List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
        //    pairList.add(new BasicNameValuePair("name", "admin"));
        //    pairList.add(new BasicNameValuePair("pass", "123456"));
        //    httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));

        //创建一个entry对象
        StringEntity entity = new StringEntity( jsonParam.toString(), "UTF-8" );

        entity.setContentEncoding( "UTF-8" );
        entity.setContentType( "application/json" );
        post.setEntity( entity );

        //执行post请求
        response = closeableHttpClient.execute( post );

        //通过EntityUtils获取返回内容
        if (response.getStatusLine().getStatusCode() == 200) {
            String responseBody = EntityUtils.toString( response.getEntity(), "UTF-8" );
            System.out.println( "verifyCodeResponseBody:" + responseBody );
            //转换成json,根据合法性返回json或者字符串
            ReadContext readContext = JsonPath.parse( responseBody );
            String success = readContext.read( "$.msg" );
            if (success.equals( "验证码不正确" )){
                uuid = UUID.randomUUID().toString();
            }
            Assert.assertEquals( success,"success" );

            /*
            if (!success.equals( "success" )){
                for(int i=0 ; i<10 ; i++){
                    if(!flag){
                        verifyCode(flag);
                    }
                }
            }else{
                flag = Boolean.TRUE;
            }

             */
        } else {
            logger.info( "获取验证码失败。。。。。。。。" );
        }
        return captcha;
    }


    //百度ocr识别验证码
    public static String getVerificationCode(String imageUrl) {
        //获取accessToken
        String accessToken = getAccessToken();
        String verificationCode = "";
        String targetUrl = ocrUrl + "?access_token=" + accessToken;
        System.out.println( "targetUrl:" + targetUrl );
        HashMap<String, String> headers = new HashMap<>();
        headers.put( "Content-Type", "application/x-www-form-urlencoded" );
        HashMap<String, String> params = new HashMap<>();
        try {
            Thread.sleep( 100 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //对图片进行Base64编码处理
        imageUrl = encodeImgeToBase64( imageUrl );

        params.put( "image", imageUrl );

        HttpRequestData httpRequestData = new HttpRequestData();
        httpRequestData.setHeaders( headers );
        httpRequestData.setRequestMethod( "post" );
        httpRequestData.setParams( params );
        httpRequestData.setRequestUrl( targetUrl );
        HttpResponse response = HttpClientUtils.execute( httpRequestData );

        String json = "";
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                // ReadContext readContext = JsonPath.parse( response );
                json = IOUtils.toString( response.getEntity().getContent() );
                JSONObject jsonObject = JSONObject.parseObject( json );
                JSONArray wordsResult = jsonObject.getJSONArray( "words_result" );
                verificationCode = wordsResult.getJSONObject( 0 ).getString( "words" );
                //过滤除数字，字母之外的字符
            } catch (IOException e) {
                logger.error( "请求识别失败！", e );
            }
        }
        return verificationCode;

    }


    //对图片进行Base64编码处理
    public static String encodeImgeToBase64(String imagePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            File imageFile = new File( imagePath );
            InputStream in = new FileInputStream( imageFile );
            data = new byte[in.available()];
            in.read( data );
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Data = Base64.encodeBase64String( data );
        return base64Data;
    }

}