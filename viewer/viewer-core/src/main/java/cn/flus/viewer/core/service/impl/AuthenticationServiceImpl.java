package cn.flus.viewer.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.flus.core.utils.JsonUtils;
import cn.flus.viewer.core.dto.AuthenticationToken;
import cn.flus.viewer.core.service.AuthenticationService;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Value("${autodesk.client.id}")
    private String              clientId;

    @Value("${autodesk.client.secret}")
    private String              clientSecret;

    @Value("${autodesk.api.path.viewdata}")
    private String              apiPath;

    @Override
    public String fetchAccessToken() {

        // 准备Http请求
        HttpPost httpPost = new HttpPost(apiPath + "/authentication/v1/authenticate");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("client_id", clientId));
        formParams.add(new BasicNameValuePair("client_secret", clientSecret));
        formParams.add(new BasicNameValuePair("grant_type", "client_credentials"));
        HttpEntity requestEntity = null;
        requestEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        httpPost.setEntity(requestEntity);

        // 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String responseBody = null;
        try {

            // 发送http请求
            response = httpClient.execute(httpPost);

            // 接收返回数据
            responseBody = EntityUtils.toString(response.getEntity());
            logger.info(responseBody);

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }

        // 解析返回值
        AuthenticationToken authenticationToken = JsonUtils.toObject(responseBody, AuthenticationToken.class);
        if (authenticationToken == null) {
            return null;
        }
        return authenticationToken.getAccess_token();
    }
}
