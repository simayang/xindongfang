package com.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class getCookie {
    private final static String CHAR_SET = "UTF-8";
    private final static String CONTENT_TYPE_JSON = "application/json";

    static final String url = "https://entry-qa.dfzxvip.com/api/login/account";

    private static CloseableHttpClient getCloseableHttpClient() {
        return HttpClientBuilder.create().build();
    }


    private static void CloseResource(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) throws IOException {
        if (null != httpResponse) {
            httpResponse.close();
        }
        if (null != httpClient) {
            httpClient.close();
        }
    }

    public static String doPost(String url, String jsonStr) {

        CloseableHttpClient httpClient = getCloseableHttpClient();
        CloseableHttpResponse httpResponse = null;
        String result = null;
        String cookie = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(jsonStr, CHAR_SET);
            stringEntity.setContentType(CONTENT_TYPE_JSON);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                System.out.println(result);
                JSONObject object = JSONObject.parseObject(result);
                cookie = object.getString("data");
                System.out.println(cookie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cookie;
    }

    public static void main(String[] args) {
        Map<String, String> body = new HashMap<>();
        body.put("userName", "lvhaichao");
        body.put("password", "123");
        getCookie.doPost(url, JSON.toJSONString(body));
    }
}
