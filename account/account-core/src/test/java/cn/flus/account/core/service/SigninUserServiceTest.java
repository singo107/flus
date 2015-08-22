package cn.flus.account.core.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.flus.account.core.dto.SigninUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class SigninUserServiceTest {

    @Autowired
    private SigninUserService signinUserService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String key = "at";
        SigninUser signinUser = new SigninUser(1);
        signinUser.setLoginname("singo");
        signinUserService.put(key, signinUser);
        SigninUser signinUserAfter = signinUserService.get(key);
        Assert.assertEquals(signinUser.getId(), signinUserAfter.getId());
        Assert.assertEquals(signinUser.getLoginname(), signinUserAfter.getLoginname());
    }
}
