package cn.flus.cms.core.dao;

import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

public interface TopicDao {

    TopicEntity get(Integer id);

    PagedList<TopicEntity> getPagedOrderdByCategoryId(Integer categoryId, Integer status, String orderFiled,
                                                      String orderType, Page page);

    Integer insert(TopicEntity entity);

    Integer update(TopicEntity entity);

    Integer updateDeleteFlag(Integer[] ids);

}
