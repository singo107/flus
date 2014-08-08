package cn.flus.core.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdentityCardUtilsTest {

    @Test
    public void test() throws Exception {
        String credentialNumber1 = "420527198311115318";
        String credentialNumber2 = "420527198311115319";
        boolean result1 = IdentityCardUtils.isValidIdentityCardNumber(credentialNumber1);
        boolean result2 = IdentityCardUtils.isValidIdentityCardNumber(credentialNumber2);
        assertEquals(true, result1);
        assertEquals(false, result2);
    }
}
