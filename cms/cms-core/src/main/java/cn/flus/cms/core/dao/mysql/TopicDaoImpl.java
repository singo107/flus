package cn.flus.cms.core.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.cms.core.dao.TopicDao;
import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dao.mapper.TopicEntityMapper;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

@Service("topicDao")
public class TopicDaoImpl implements TopicDao {

    @Autowired
    private TopicEntityMapper topicEntityMapper;

    @Override
    public TopicEntity get(Integer id) {
        return topicEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insert(TopicEntity entity) {
        topicEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Integer update(TopicEntity entity) {
        return topicEntityMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public Integer updateDeleteFlag(Integer[] ids) {
        return topicEntityMapper.updateDeleteFlag(ids);
    }

    @Override
    public PagedList<TopicEntity> getPagedOrderdByCategoryId(Integer categoryId, Integer status, String orderFiled,
                                                             String orderType, Page page) {
        int totalCount = topicEntityMapper.countSelectByOrderdCategoryId(categoryId, status);
        page.setTotalCount(totalCount);
        if (totalCount <= 0) {
            return null;
        }
        List<TopicEntity> list = topicEntityMapper.selectByOrderdCategoryId(categoryId, status, orderFiled, orderType,
                                                                            page.getStartIndex(), page.getPageSize());
        return new PagedList<TopicEntity>(list, page);
    }

}
