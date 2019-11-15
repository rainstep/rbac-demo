package com.example.rbacdemo.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String responseText = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                responseText = EntityUtils.toString(httpEntity, Consts.UTF_8);
                EntityUtils.consume(httpEntity);
//                logger.info("url : {}", url);
//                logger.info("response : {}", responseText);
            } else {
                logger.error("url : {}", url);
                logger.error("error httpCode : {}", statusLine.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("url : {}", url);
            logger.error("error message : {}", ExceptionUtils.getRootCauseMessage(e));
        } finally {
            dispose(httpClient, httpResponse);
        }
        return responseText;
    }

    public static String formPost(String url, Map<String, String> paramMap) {
        return formPost(url, paramMap, null);
    }

    public static String formPost(String url, Map<String, String> paramMap, Map<String, String> headerMap) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params  = new ArrayList<>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                params.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        if (headerMap != null) {
            for (String headName : headerMap.keySet()) {
                httpPost.setHeader(headName, headerMap.get(headName));
            }
        }
        return postSend(httpPost);
    }

    public static String jsonPost(String url, String requestBody) {
        return jsonPost(url, requestBody, null);
    }

    public static String jsonPost(String url, String requestBody, Map<String, String> headerMap) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(requestBody, Consts.UTF_8);
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        if (headerMap != null) {
            for (String headName : headerMap.keySet()) {
                httpPost.setHeader(headName, headerMap.get(headName));
            }
        }
        return postSend(httpPost);
    }

    private static String postSend(HttpPost httpPost) {
        if (httpPost == null) return null;
        String url = httpPost.getURI().toString();
        String requestBody = null;
        String responseText = null;
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            requestBody = EntityUtils.toString(httpPost.getEntity(), Consts.UTF_8);
            httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                responseText = EntityUtils.toString(httpEntity, Consts.UTF_8);
                EntityUtils.consume(httpEntity);
//                logger.info("url : {}", url);
//                logger.info("requestBody : {}", requestBody);
//                logger.info("httpResponse : {}", responseText);
            } else {
                logger.error("url : {}", url);
                logger.error("requestBody : {}", requestBody);
                logger.error("error httpCode : {}", statusLine.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("url : {}", url);
            logger.error("requestBody : {}", requestBody);
            logger.error("error message : {}", ExceptionUtils.getRootCauseMessage(e));
        } finally {
            dispose(httpClient, httpResponse);
        }
        return responseText;
    }

    private static void dispose(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse){
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("error message : {}", ExceptionUtils.getRootCauseMessage(e));
            }
        }
        if (null != httpResponse) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("error message : {}", ExceptionUtils.getRootCauseMessage(e));
            }
        }
    }

}
