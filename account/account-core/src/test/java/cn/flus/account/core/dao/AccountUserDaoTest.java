package cn.flus.account.core.dao;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.account.core.dao.domain.AccountUserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class AccountUserDaoTest {

    @Autowired
    private AccountUserDao accountUserDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        AccountUserEntity entity = new AccountUserEntity();
        entity.setLoginname("singo1071@qq.com");
        entity.setCreateTime(new Date());
        entity.setStatus(0);
        accountUserDao.insert(entity);
        entity = accountUserDao.get(1);
        System.out.println(entity.getEmail());
    }

}
