package cn.flus.cms.core.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.cms.core.dao.AttachmentDao;
import cn.flus.cms.core.dao.domain.AttachmentEntity;
import cn.flus.cms.core.dao.mapper.AttachmentEntityMapper;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

@Service("attachmentDao")
public class AttachmentDaoImpl implements AttachmentDao {

    @Autowired
    private AttachmentEntityMapper attachmentEntityMapper;

    @Override
    public Integer insert(AttachmentEntity entity) {
        attachmentEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Integer updateDeleteFlag(Integer[] ids) {
        return attachmentEntityMapper.updateDeleteFlag(ids);
    }

    @Override
    public PagedList<AttachmentEntity> getPagedByQuery(String title, String url, Page page) {

        int totalCount = attachmentEntityMapper.countSelectBySelective(title, url);
        page.setTotalCount(totalCount);
        if (totalCount <= 0) {
            return null;
        }
        List<AttachmentEntity> list = attachmentEntityMapper.selectBySelective(title, url, page.getStartIndex(),
                                                                               page.getPageSize());
        return new PagedList<AttachmentEntity>(list, page);
    }

}
