package cn.flus.cms.core.service;

import java.io.File;

import cn.flus.cms.core.dao.domain.AttachmentEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

public interface AttachmentService {

    /**
     * 上传附件
     * 
     * @param file
     * @param uploaderId
     * @param uploader
     * @return
     */
    AttachmentEntity upload(File file, Long uploaderId, String uploader);

    /**
     * 查询
     * 
     * @param title
     * @param url
     * @param page
     * @return
     */
    PagedList<AttachmentEntity> getByQuery(String title, String url, Page page);

    /**
     * 批量删除
     * 
     * @param ids
     * @return
     */
    void delete(Integer[] ids);

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    void delete(Integer id);

}
