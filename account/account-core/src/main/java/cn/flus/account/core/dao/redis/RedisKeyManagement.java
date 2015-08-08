package cn.flus.account.core.dao.redis;

import org.apache.commons.lang.StringUtils;

public abstract class RedisKeyManagement {

    private static final String SIGNIN_REDIS_KEY_PREX  = "signin."; // 用户登录的SigninUK
    private static final String CAPTCHA_REDIS_KEY_PREX = "captcha."; // Captcha的key值

    protected String signinKey(String val) {
        return redisKey(val, SIGNIN_REDIS_KEY_PREX);
    }

    protected String captchaKey(String val) {
        return redisKey(val, CAPTCHA_REDIS_KEY_PREX);
    }

    private String redisKey(String val, String prex) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return prex + val;
    }
}
