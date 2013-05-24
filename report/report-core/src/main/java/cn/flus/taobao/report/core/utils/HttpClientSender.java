package cn.flus.taobao.report.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhouxing 2013-5-24
 */
public class HttpClientSender {

    private static final Logger logger             = LoggerFactory.getLogger(HttpClientSender.class);
    private static final String FORM_ENTITY_ENCODE = "UTF-8";

    /**
     * 通过HttpClient发送get请求，并返回response的文本内容
     * 
     * @param url
     * @return
     */
    public static String get(String url) {

        // HttpClient请求开始
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("http client get started, url: " + url);

        // 发送HttpClient请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        String output = null;
        try {
            response = httpClient.execute(httpGet);
            output = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            logger.error("http client error", e);
        } catch (IOException e) {
            logger.error("http client error", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        // HttpClient请求结束
        watch.stop();
        logger.info("http client get successed, cost time: " + watch.getTime() + ", output: " + output);

        return output;
    }

    /**
     * 通过HttpClient发送post请求，并返回response的文本内容
     * 
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params) {

        // HttpClient请求开始
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("http client post started, url: " + url + ", params: " + ToStringBuilder.reflectionToString(params));

        // post params
        List<NameValuePair> formParams = null;
        if (MapUtils.isNotEmpty(params)) {
            formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                formParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }

        // 发送HttpClient请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formParams, FORM_ENTITY_ENCODE);
        } catch (UnsupportedEncodingException e) {
            logger.error(FORM_ENTITY_ENCODE + " unsupported", e);
        }
        httpPost.setEntity(entity);

        HttpResponse response = null;
        String output = null;
        try {
            response = httpClient.execute(httpPost);
            output = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            logger.error("http client error", e);
        } catch (IOException e) {
            logger.error("http client error", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        // HttpClient请求结束
        watch.stop();
        logger.info("http client post successed, cost time: " + watch.getTime() + ", output: " + output);

        return output;
    }
}
