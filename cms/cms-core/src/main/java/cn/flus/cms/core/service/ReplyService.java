package cn.flus.cms.core.service;

import cn.flus.cms.core.dao.domain.ReplyEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

public interface ReplyService {

    /**
     * 添加回复
     * 
     * @param topicId
     * @param content
     * @param authorId
     * @param author
     * @return
     */
    ReplyEntity add(Integer topicId, String content, Long authorId, String author);

    /**
     * 根据主题返回回复列表
     * 
     * @param topicId
     * @param status
     * @param page
     * @return
     */
    PagedList<ReplyEntity> getByTopicId(Integer topicId, Integer status, Page page);

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
