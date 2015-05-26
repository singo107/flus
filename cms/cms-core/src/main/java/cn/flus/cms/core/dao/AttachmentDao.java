package cn.flus.cms.core.dao;

import cn.flus.cms.core.dao.domain.AttachmentEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

public interface AttachmentDao {

    Integer insert(AttachmentEntity entity);

    Integer updateDeleteFlag(Integer[] ids);

    PagedList<AttachmentEntity> getPagedByQuery(String title, String url, Page page);
}
