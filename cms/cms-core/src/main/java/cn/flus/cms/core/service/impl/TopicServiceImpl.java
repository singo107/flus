package cn.flus.cms.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.cms.core.dao.TopicDao;
import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;
import cn.flus.cms.core.enums.OrderType;
import cn.flus.cms.core.enums.TopicStatus;
import cn.flus.cms.core.enums.TopticOrderFiled;
import cn.flus.cms.core.enums.YesOrNo;
import cn.flus.cms.core.exceptions.TopicNotFoundException;
import cn.flus.cms.core.service.TopicService;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public TopicEntity add(TopicEntity topic) {
        topic.setDeleteFlag(YesOrNo.NO.getCode());
        Integer id = topicDao.insert(topic);
        topic.setId(id);
        return topic;
    }

    @Override
    public TopicEntity get(Integer id) {
        return topicDao.get(id);
    }

    @Override
    public PagedList<TopicEntity> getByCategoryId(Integer categoryId, Integer status, TopticOrderFiled orderFiled,
                                                  OrderType orderType, Page page) {
        return topicDao.getPagedOrderdByCategoryId(categoryId, status, orderFiled.getCode(), orderType.getCode(), page);
    }

    @Override
    public void update(TopicEntity topic) {
        topicDao.update(topic);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {

        // 判断参数
        Assert.notNull(id);
        TopicStatus topicStatus = TopicStatus.parse(status);
        if (topicStatus == null) {
            throw new IllegalArgumentException("status is not correct, status=" + status);
        }

        // 读取待修改的主题
        TopicEntity topic = get(id);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + id);
        }

        // 判断是否要修改
        if (status.equals(topic.getStatus())) {
            return;
        }

        // 修改
        topic.setStatus(topicStatus.getValue());
        update(topic);
    }

    @Override
    public void updatePlaceTop(Integer id, Boolean placeTop) {

        // 判断参数
        Assert.notNull(id);

        // 读取待修改的主题
        TopicEntity topic = get(id);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + id);
        }

        // 修改
        if (placeTop) {
            topic.setPlaceTop(YesOrNo.YES.getCode());
        } else {
            topic.setPlaceTop(YesOrNo.NO.getCode());
        }
        update(topic);
    }

    @Override
    public void updateViewCount(Integer id, Integer increase) {

        // 判断参数
        Assert.notNull(id);

        // 读取待修改的主题
        TopicEntity topic = get(id);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + id);
        }

        // 判断是否需要修改
        if (increase == null || increase <= 0) {
            return;
        }

        // 修改
        topic.setViewCount(increase + topic.getViewCount());
        update(topic);
    }

    @Override
    public void updateCommentsCount(Integer id, Integer increase) {

        // 判断参数
        Assert.notNull(id);

        // 读取待修改的主题
        TopicEntity topic = get(id);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + id);
        }

        // 判断是否需要修改
        if (increase == null || increase <= 0) {
            return;
        }

        // 修改
        topic.setReplyCount(increase + topic.getReplyCount());
        update(topic);
    }

    @Override
    public void updatePraiseCount(Integer id, Integer increase) {

        // 判断参数
        Assert.notNull(id);

        // 读取待修改的主题
        TopicEntity topic = get(id);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + id);
        }

        // 判断是否需要修改
        if (increase == null || increase <= 0) {
            return;
        }

        // 修改
        topic.setPraiseCount(increase + topic.getPraiseCount());
        update(topic);
    }

    @Override
    public void delete(Integer[] ids) {
        topicDao.updateDeleteFlag(ids);
    }

    @Override
    public void delete(Integer id) {
        delete(new Integer[] { id });
    }

}
