package cn.flus.account.core.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:test-spring-context-config.xml" })
public class CaptchaServiceTest {

    @Autowired
    private CaptchaService captchaService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {

        String key = "a";
        String code = "1111";

        byte[] b = captchaService.generateCaptcha(key);
        int len = 0;
        if (b != null) {
            len = b.length;
        }
        Assert.assertEquals(true, len > 0);

        boolean as = captchaService.validateCaptcha(key, code);
        Assert.assertEquals(true, as);
    }
}
