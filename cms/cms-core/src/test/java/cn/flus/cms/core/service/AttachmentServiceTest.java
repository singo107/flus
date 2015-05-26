package cn.flus.cms.core.service;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.cms.core.dao.domain.AttachmentEntity;
import cn.flus.cms.core.dto.page.Page;
import cn.flus.cms.core.dto.page.PagedList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class AttachmentServiceTest {

    @Autowired
    private AttachmentService attachmentService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Test
    public void test1() {
        attachmentService.upload(new File("F:\\abc.jpg"), 1l, "singo");
    }

    // @Test
    public void test2() {
        attachmentService.delete(8);
    }

    @Test
    public void test3() {
        PagedList<AttachmentEntity> pagedList = attachmentService.getByQuery("bda", null, new Page(2));
        List<AttachmentEntity> list = pagedList.getList();
        for (AttachmentEntity e : list) {
            System.out.println(e.getTitle());
        }
    }
}
