package cn.flus.viewer.core.service.impl;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import cn.flus.viewer.core.service.Upload2QiniuService;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service("upload2QiniuService")
public class Upload2QiniuServiceImpl implements Upload2QiniuService {

    private static final String APP_KEY    = "8v1RhdiSyWa0jBGbOT7trQf6PKMaRCePtNb-77PM";
    private static final String APP_SECRET = "4Ols_P5is1IArfhKe34i-Jef2EpJQ2GfoztdYQSv";

    @Override
    public String upload(InputStream inputStream, String fileName) {

        UploadManager uploadManager = new UploadManager();

        try {
            Response res = uploadManager.put(new File("e:\\tmp\\read.txt"), "read.txt", getUpToken0());
        } catch (QiniuException e) {
        }

        return null;
    }

    private String getUpToken0() {
        Auth auth = Auth.create(APP_KEY, APP_SECRET);
        return auth.uploadToken("flus");
    }

}
