package cn.flus.cms.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.cms.core.dao.ReplyDao;
import cn.flus.cms.core.dao.domain.CategoryEntity;
import cn.flus.cms.core.dao.domain.ReplyEntity;
import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;
import cn.flus.cms.core.enums.ReplyStatus;
import cn.flus.cms.core.enums.YesOrNo;
import cn.flus.cms.core.exceptions.CategoryNotFoundException;
import cn.flus.cms.core.exceptions.TopicNotAllowReplyException;
import cn.flus.cms.core.exceptions.TopicNotFoundException;
import cn.flus.cms.core.service.CategoryService;
import cn.flus.cms.core.service.ReplyService;
import cn.flus.cms.core.service.TopicService;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao        replyDao;

    @Autowired
    private TopicService    topicService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ReplyEntity add(Integer topicId, String content, Long authorId, String author) {

        // 判断参数
        Assert.notNull(topicId);
        Assert.hasText(content);
        Assert.notNull(authorId);
        Assert.notNull(author);

        // 判断主题是否存在
        TopicEntity topic = topicService.get(topicId);
        if (topic == null) {
            throw new TopicNotFoundException("topic not found, id=" + topicId);
        }

        // 判断主题所在栏目是否存在
        CategoryEntity category = categoryService.get(topic.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException("category not found, id=" + topic.getCategoryId());
        }

        // 判断主题是否允许回复
        if (!YesOrNo.YES.getCode().equals(category.getAllowReply())
            || !YesOrNo.YES.getCode().equals(topic.getAllowReply())) {
            throw new TopicNotAllowReplyException("topic not allow reply, id=" + topicId);
        }

        ReplyEntity reply = new ReplyEntity();
        reply.setTopicId(topicId);

        // 判断是否需要审计
        if (YesOrNo.YES.getCode().equals(category.getReplyAudit())) {
            reply.setStatus(ReplyStatus.TOAUDIT.getValue());
        } else {
            reply.setStatus(ReplyStatus.PASSED.getValue());
        }

        reply.setAuthorId(authorId);
        reply.setAuthor(author);
        reply.setRecommend(YesOrNo.NO.getCode());
        reply.setPraiseCount(0);
        reply.setCreateTime(new Date());
        reply.setContent(content);
        reply.setDeleteFlag(YesOrNo.NO.getCode());
        Integer id = replyDao.insert(reply);
        reply.setId(id);
        return reply;
    }

    @Override
    public PagedList<ReplyEntity> getByTopicId(Integer topicId, Integer status, Page page) {
        return replyDao.getPagedByTopicId(topicId, status, page);
    }

    @Override
    public void delete(Integer[] ids) {
        replyDao.updateDeleteFlag(ids);
    }

    @Override
    public void delete(Integer id) {
        delete(new Integer[] { id });
    }

    @Override
    public void updateStatus(Integer[] ids, Integer status) {
        replyDao.updateStatus(ids, status);
    }

    @Override
    public void updateRecommend(Integer[] ids, Boolean recommend) {
        if (recommend) {
            replyDao.updateRecommend(ids, YesOrNo.YES.getCode());
        } else {
            replyDao.updateRecommend(ids, YesOrNo.NO.getCode());
        }
    }

}
