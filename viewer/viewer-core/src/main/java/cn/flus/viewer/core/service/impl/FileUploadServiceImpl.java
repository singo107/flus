package cn.flus.viewer.core.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.flus.core.utils.JsonUtils;
import cn.flus.viewer.core.dto.FileObject;
import cn.flus.viewer.core.dto.UrnKey;
import cn.flus.viewer.core.service.AuthenticationService;
import cn.flus.viewer.core.service.FileUploadService;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger   logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${autodesk.api.path.viewdata}")
    private String                apiPath;

    @Value("${autodesk.bucket.key}")
    private String                bucketKey;

    @Override
    public String uploadAndConvert(InputStream inputStream, String fileName) {

        // 获取token
        String token = authenticationService.fetchAccessToken();
        if (StringUtils.isBlank(token)) {
            return null;
        }

        // 上传文件
        FileObject fileObject = upload(inputStream, fileName, token);
        if (fileObject == null) {
            return null;
        }

        // // 生成base64的urn
        String urnBase64 = Base64.encodeBase64String(fileObject.getObjectId().getBytes());
        logger.info("upload file urn: " + urnBase64);

        // 转换
        boolean result = startConvert(urnBase64, token);
        if (!result) {
            return null;
        }
        return urnBase64;
    }

    @Override
    public int fetchConvertProgress(String urn) {
        return 0;
    }

    private FileObject upload(InputStream inputStream, String fileName, String token) {

        // 准备Http请求
        HttpPut httpPut = new HttpPut(apiPath + "/oss/v2/buckets/" + bucketKey + "/objects/" + fileName);
        httpPut.setHeader("Authorization", "Bearer " + token);
        InputStreamEntity requestEntity = new InputStreamEntity(inputStream, -1);
        requestEntity.setContentType("binary/octet-stream");
        requestEntity.setChunked(true);
        httpPut.setEntity(requestEntity);

        // 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String responseBody = null;

        try {

            // 发送http请求
            response = httpClient.execute(httpPut);

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
        return JsonUtils.toObject(responseBody, FileObject.class);
    }

    private boolean startConvert(String urn, String token) {

        // 准备Http请求
        HttpPost httpPost = new HttpPost(apiPath + "/viewingservice/v1/register");
        httpPost.setHeader("Authorization", "Bearer " + token);
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(JsonUtils.toJson(new UrnKey(urn)), ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

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

        // 返回是否成功
        return (responseBody.indexOf("Success") > -1);
    }
}
