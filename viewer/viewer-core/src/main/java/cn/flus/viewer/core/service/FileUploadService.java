package cn.flus.viewer.core.service;

import java.io.InputStream;

public interface FileUploadService {

    /**
     * 上传文件到Autodesk并启动转换
     * 
     * @param inputStream
     * @param fileName
     * @return
     */
    String uploadAndConvert(InputStream inputStream, String fileName);

    /**
     * 查看文件转换进度
     * 
     * @param urn
     * @return 0-代表尚未开始，100-代表完成，0~100之间代表转换百分比
     */
    int fetchConvertProgress(String urn);
}
