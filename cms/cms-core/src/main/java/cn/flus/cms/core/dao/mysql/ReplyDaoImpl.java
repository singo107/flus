package cn.flus.cms.core.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.cms.core.dao.ReplyDao;
import cn.flus.cms.core.dao.domain.ReplyEntity;
import cn.flus.cms.core.dao.mapper.ReplyEntityMapper;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

@Service("replyDao")
public class ReplyDaoImpl implements ReplyDao {

    @Autowired
    private ReplyEntityMapper replyEntityMapper;

    @Override
    public PagedList<ReplyEntity> getPagedByTopicId(Integer topicId, Integer status, Page page) {
        int totalCount = replyEntityMapper.countSelectByOrderdTopicId(topicId, status);
        page.setTotalCount(totalCount);
        if (totalCount <= 0) {
            return null;
        }
        List<ReplyEntity> list = replyEntityMapper.selectByOrderdTopicId(topicId, status, page.getStartIndex(),
                                                                         page.getPageSize());
        return new PagedList<ReplyEntity>(list, page);
    }

    @Override
    public Integer insert(ReplyEntity entity) {
        replyEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Integer updateDeleteFlag(Integer[] ids) {
        return replyEntityMapper.updateDeleteFlag(ids);
    }

}
