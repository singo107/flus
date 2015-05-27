package cn.flus.cms.core.service;

import java.util.List;

import cn.flus.cms.core.dao.domain.CategoryEntity;

public interface CategoryService {

    /**
     * 添加一个节点
     * 
     * @param name
     * @param parentId
     * @param allowTopic
     * @param topicAudit
     * @param allowReply
     * @param replyAudit
     * @param weight
     * @param creatorId
     * @param creator
     * @return
     */
    CategoryEntity add(String name, Integer parentId, Boolean allowTopic, Boolean topicAudit, Boolean allowReply,
                       Boolean replyAudit, Integer weight, Long creatorId, String creator);

    /**
     * 修改一个节点
     * 
     * @param id
     * @param name
     * @param parentId
     * @param status
     * @param allowTopic
     * @param topicAudit
     * @param allowReply
     * @param replyAudit
     * @param weight
     * @return
     */
    CategoryEntity update(Integer id, String name, Integer parentId, Integer status, Boolean allowTopic,
                          Boolean topicAudit, Boolean allowReply, Boolean replyAudit, Integer weight);

    /**
     * 读取单个节点
     * 
     * @param id
     * @return
     */
    CategoryEntity get(Integer id);

    /**
     * 获取排序过的列表
     * 
     * @param filterDisable
     * @return
     */
    List<CategoryEntity> getAllSorted(boolean filterDisable);

    /**
     * 读取同一个父亲的兄弟节点
     * 
     * @param parentId
     * @param filterDisable
     * @return
     */
    List<CategoryEntity> getByParentId(Integer parentId, boolean filterDisable);

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
