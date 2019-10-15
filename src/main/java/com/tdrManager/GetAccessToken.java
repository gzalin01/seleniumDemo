package com.tdrManager;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetAccessToken {
    //获取accessToken
    public  String getAccessToken() {
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
}
