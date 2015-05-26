package cn.flus.cms.core.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.cms.core.dao.domain.CategoryEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Test
    public void test1() {
        String name = "2-3-1";
        Integer parentId = 20;
        Boolean comments = true;
        Long creatorId = 1l;
        String creator = "singo";
        Integer weight = 1;
        CategoryEntity categoryEntity = categoryService.add(name, parentId, comments, weight, creatorId, creator);
        System.out.println(categoryEntity);
    }

    // @Test
    public void test2() {
        List<CategoryEntity> list = categoryService.getAllSorted(false);
        for (CategoryEntity e : list) {
            System.out.println(e.getName());
        }
    }

    // @Test
    public void test3() {
        List<CategoryEntity> list = categoryService.getByParentId(17, true);
        for (CategoryEntity e : list) {
            System.out.println(e.getName());
        }
    }

    // @Test
    public void test4() {
        categoryService.delete(17);
    }

    @Test
    public void test5() {
        List<CategoryEntity> list = categoryService.getAllSorted(false);
        for (CategoryEntity e : list) {
            System.out.println(e.getName());
        }
        System.out.println("----");
        categoryService.update(17, null, 16, null, null, null);
        list = categoryService.getAllSorted(true);
        for (CategoryEntity e : list) {
            System.out.println(e.getName());
        }
    }

}
