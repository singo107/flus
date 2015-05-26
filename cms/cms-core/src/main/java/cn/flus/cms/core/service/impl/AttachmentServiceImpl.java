package cn.flus.cms.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.cms.core.dao.AttachmentDao;
import cn.flus.cms.core.dao.domain.AttachmentEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;
import cn.flus.cms.core.enums.YesOrNo;
import cn.flus.cms.core.exceptions.FileExtNotSupportException;
import cn.flus.cms.core.exceptions.FileLengthExceedException;
import cn.flus.cms.core.exceptions.FileUploadFailedException;
import cn.flus.cms.core.service.AttachmentService;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

    // 允许上传的图片的文件类型
    private static final String[] FILE_TYPE          = new String[] { "html", "htm", "css", "js", "jpg", "gif", "bmp",
            "png", "swf", "mp4", "pdf", "txt"       };

    // 允许上传的文件大小
    private static final int      UPLOAD_FILE_LENGTH = 10 * 1024 * 1024;

    // 文件分隔符
    private static final String   FILE_SEPARATOR     = System.getProperties().getProperty("file.separator");

    @Value("${path.upload.file}")
    private String                fileUploadPrex;

    @Value("${path.url.attachment}")
    private String                attachmentUrl;

    // CMS系统的上传路径
    private static final String   CMS_PATH           = "upload";

    @Autowired
    private AttachmentDao         attachmentDao;

    @Override
    public AttachmentEntity upload(File file, Long uploaderId, String uploader) {

        // 判断参数
        Assert.notNull(file);
        Assert.notNull(uploaderId);
        Assert.hasText(uploader);
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }

        // 上传
        String url = upload(file);

        // DB
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setTitle(file.getName());
        attachment.setUrl(url);
        attachment.setFileSize(file.length());
        attachment.setUploaderId(uploaderId);
        attachment.setUploader(uploader);
        attachment.setUploadTime(new Date());
        attachment.setDeleteFlag(YesOrNo.NO.getCode());
        Integer id = attachmentDao.insert(attachment);
        attachment.setId(id);
        return attachment;
    }

    private String upload(File file) {

        // 上传文件类型校验
        if (!FilenameUtils.isExtension(file.getName(), FILE_TYPE)) {
            throw new FileExtNotSupportException("file extention not support, ext=" + file.getName());
        }

        // 上传文件大小校验
        long fileLength = file.length();
        if (fileLength > UPLOAD_FILE_LENGTH) {
            throw new FileLengthExceedException("file length exceed, length=" + fileLength);
        }

        // 计算文件目标上传路径
        String destFilePath = getDestFilePath(file.getName());
        String destFilePathReplace = destFilePath.replace("/", FILE_SEPARATOR);
        String destFilePathResult = fileUploadPrex + FILE_SEPARATOR + destFilePathReplace;

        // 把文件上传到目标路径
        File destFile = new File(destFilePathResult);
        try {
            byte[] originFlieBytes = FileUtils.readFileToByteArray(file);
            FileUtils.writeByteArrayToFile(destFile, originFlieBytes);
        } catch (IOException e) {
            throw new FileUploadFailedException("file upload failed, file=" + file);
        }

        return attachmentUrl + "/" + destFilePath;
    }

    /**
     * 获取文件的目标路径
     * 
     * @param fileName
     * @return
     */
    private String getDestFilePath(String fileName) {
        Date current = new Date();
        String path = new SimpleDateFormat("yyyy/MM/dd").format(current);
        String extension = FilenameUtils.getExtension(fileName);
        String name = Long.toString(current.getTime());
        return CMS_PATH + "/" + path + "/" + name + "." + extension;
    }

    @Override
    public PagedList<AttachmentEntity> getByQuery(String title, String url, Page page) {
        return attachmentDao.getPagedByQuery(title, url, page);
    }

    @Override
    public void delete(Integer[] ids) {
        attachmentDao.updateDeleteFlag(ids);
    }

    @Override
    public void delete(Integer id) {
        delete(new Integer[] { id });
    }

}
