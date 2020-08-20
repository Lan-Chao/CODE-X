package com.silent.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author zhaochangren
 * @Title: HttpUtils
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/7/7 18:09
 */

@Slf4j
public class HttpUtils {

    public static JSONObject sendGet(String url) {
        log.info("sendGet url:{}", url);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();
        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpGet get = new HttpGet(url);
        JSONObject response = null;
        try {
            HttpResponse res = httpclient.execute(get);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                /** 注意编码方式 */
                String result = EntityUtils.toString(res.getEntity(), "utf-8");
                if (result != null){
                    response = JSONObject.parseObject(result);
                }
            } else {
                JSONObject error = new JSONObject();
                error.put("code", res.getStatusLine().getStatusCode());
                error.put("message", res.getStatusLine().getReasonPhrase());
                log.info("sendGet fail, info:{}", error);
                return null;
            }
        } catch (Exception e) {
            log.error("sendGet param:{},error:", url ,e);
        }
        return response;
    }


    public static JSONObject sendPost(String url, JSONObject json, String token) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .build();
        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentType("application/json");
            s.setContentEncoding("UTF-8");
            post.setEntity(s);
            if (token != null){
                post.setHeader("X-Access-Token", token);
            }
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                if (result != null){
                    response = JSONObject.parseObject(result);
                }
            } else {
                JSONObject error = new JSONObject();
                error.put("code", res.getStatusLine().getStatusCode());
                error.put("message", res.getStatusLine().getReasonPhrase());
                log.info("sendGet fail, info:{}", error);
                return null;
            }
        } catch (Exception e) {
            log.error("sendPost error:", e);
        }
        return response;
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
