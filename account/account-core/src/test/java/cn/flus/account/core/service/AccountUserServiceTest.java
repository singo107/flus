package cn.flus.account.core.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.account.core.dao.domain.AccountUserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class AccountUserServiceTest {

    @Autowired
    private AccountUserService accountUserService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        String loginname = "ak2";
        String password = "000000";
        AccountUserEntity accountUserEntity = accountUserService.signup(loginname, password);
        System.out.println(accountUserEntity.getId());
        boolean as = accountUserService.checkPassword(loginname, password);
        Assert.assertEquals(true, as);
    }
}
