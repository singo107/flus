package cn.flus.cms.core.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.cms.core.dao.domain.TopicEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;
import cn.flus.cms.core.enums.OrderType;
import cn.flus.cms.core.enums.TopicStatus;
import cn.flus.cms.core.enums.TopticOrderFiled;
import cn.flus.cms.core.enums.YesOrNo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        TopicEntity topic = new TopicEntity();
        topic.setCategoryId(17);
        topic.setTitle("title");
        topic.setSummary("summary");
        topic.setThumbnail("thumbnail");
        topic.setContent("content");
        topic.setKeywords("keywords");
        topic.setAuthorId(1l);
        topic.setAuthor("author");
        topic.setSource("source");
        topic.setStatus(TopicStatus.DRAFT.getValue());
        topic.setLinkOut(YesOrNo.NO.getCode());
        topic.setLinkUrl("linkUrl");
        topic.setAllowReply(YesOrNo.NO.getCode());
        topic.setPlaceTop(YesOrNo.NO.getCode());
        topic.setViewCount(0);
        topic.setReplyCount(0);
        topic.setPraiseCount(0);
        topic.setCreateTime(new Date());
        topic.setPublishTime(new Date());
        topic.setLastReplyerId(2l);
        topic.setLastReplyer("lastReplyer");
        topic.setLastReplyTime(new Date());
        TopicEntity te = topicService.add(topic);
        System.out.println(topicService.get(te.getId()));
    }

    @Test
    public void test2() {
        Page page = new Page(10);
        PagedList<TopicEntity> pagedList = topicService.getByCategoryId(17, null, TopticOrderFiled.RT, OrderType.DESC,
                                                                        page);
        List<TopicEntity> list = pagedList.getList();
        for (TopicEntity e : list) {
            System.out.println(e);
        }
    }

    // @Test
    public void test3() {
        TopicEntity e = topicService.get(1);
        e.setLastReplyerId(33l);
        e.setLastReplyer("lastReplyer");
        e.setLastReplyTime(new Date());
        topicService.update(e);
    }

    // @Test
    public void test4() {
        topicService.updateStatus(5, TopicStatus.OFFLINE.getValue());
        topicService.updatePlaceTop(5, true);
        topicService.updatePraiseCount(5, 81);
    }
}
