package cn.flus.cms.core.dao;

import cn.flus.cms.core.dao.domain.ReplyEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

public interface ReplyDao {

    PagedList<ReplyEntity> getPagedByTopicId(Integer topicId, Integer status, Page page);

    Integer insert(ReplyEntity entity);

    Integer updateDeleteFlag(Integer[] ids);

}
