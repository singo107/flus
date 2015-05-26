package cn.flus.cms.core.service;

import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;
import cn.flus.cms.core.enums.OrderType;
import cn.flus.cms.core.enums.TopticOrderFiled;

public interface TopicService {

    /**
     * 新增主题
     * 
     * @param topic
     * @return
     */
    TopicEntity add(TopicEntity topic);

    /**
     * 获取单个主题
     * 
     * @param id
     * @return
     */
    TopicEntity get(Integer id);

    /**
     * 按栏目查询主题列表
     * 
     * @param categoryId
     * @param status
     * @param orderFiled
     * @param orderType
     * @param page
     * @return
     */
    PagedList<TopicEntity> getByCategoryId(Integer categoryId, Integer status, TopticOrderFiled orderFiled,
                                           OrderType orderType, Page page);

    /**
     * 修改主题
     * 
     * @param topic
     * @return
     */
    void update(TopicEntity topic);

    /**
     * 修改主题状态
     * 
     * @param id
     * @param status
     * @return
     */
    void updateStatus(Integer id, Integer status);

    /**
     * 修改主题置顶
     * 
     * @param id
     * @param placeTop
     * @return
     */
    void updatePlaceTop(Integer id, Boolean placeTop);

    /**
     * 修改主题阅读次数
     * 
     * @param id
     * @param increase
     * @return
     */
    void updateViewCount(Integer id, Integer increase);

    /**
     * 修改主题评论次数
     * 
     * @param id
     * @param increase
     * @return
     */
    void updateCommentsCount(Integer id, Integer increase);

    /**
     * 修改主题点赞次数
     * 
     * @param id
     * @param increase
     * @return
     */
    void updatePraiseCount(Integer id, Integer increase);

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
