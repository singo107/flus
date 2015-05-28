package cn.flus.cms.core.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.cms.core.dao.domain.ReplyEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Test
    public void test1() {
        ReplyEntity replyEntity = replyService.add(1, "content", 58l, "author");
        System.out.println(replyEntity.getId());
    }

    // @Test
    public void test2() {
        PagedList<ReplyEntity> pagedList = replyService.getByTopicId(1, null, new Page());
        if (pagedList != null) {
            List<ReplyEntity> list = pagedList.getList();
            for (ReplyEntity e : list) {
                System.out.println(e);
            }
        }
    }

    @Test
    public void test3() {
        replyService.updateRecommend(new Integer[] { 1, 2, 3 }, false);
    }

    // @Test
    public void test4() {
        replyService.delete(new Integer[] { 1, 2, 3 });
    }

}
