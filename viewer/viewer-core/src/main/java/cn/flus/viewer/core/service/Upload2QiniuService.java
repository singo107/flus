package cn.flus.viewer.core.service;

import java.io.InputStream;

public interface Upload2QiniuService {

    /**
     * 上传
     * 
     * @param inputStream
     * @param fileName
     * @return
     */
    String upload(InputStream inputStream, String fileName);

}
