package cn.flus.viewer.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import cn.flus.core.utils.JsonUtils;
import cn.flus.viewer.core.dto.AuthenticationToken;
import cn.flus.viewer.core.service.AuthenticationService;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger           logger                      = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Value("${autodesk.client.id}")
    private String                        clientId;

    @Value("${autodesk.client.secret}")
    private String                        clientSecret;

    @Value("${autodesk.api.path.viewdata}")
    private String                        apiPath;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String           ACCESS_TOKEN_REDIS_KEY_PREX = "viewer.autodesk.token.";

    @PostConstruct
    public void init() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    @Override
    public String fetchAccessToken() {

        // 从缓存中读取
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN_REDIS_KEY_PREX);

        // 缓存中获取不到
        if (StringUtils.isBlank(accessToken)) {

            // 从Autodesk实时获取
            AuthenticationToken authenticationToken = fetchFromAutodesk();
            if (authenticationToken == null) {
                logger.error("access_token fetch failed.");
                return null;
            }

            // 把从Autodesk实时获取的token存入缓存
            redisTemplate.opsForValue().set(ACCESS_TOKEN_REDIS_KEY_PREX, authenticationToken.getAccess_token(),
                                            authenticationToken.getExpires_in() * 1000, TimeUnit.MILLISECONDS);
        }

        logger.info("access_token: " + accessToken);
        return accessToken;
    }

    /**
     * 从获取access token
     * 
     * @return
     */
    private AuthenticationToken fetchFromAutodesk() {

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
        return JsonUtils.toObject(responseBody, AuthenticationToken.class);
    }
}
