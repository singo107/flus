package cn.flus.account.core.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.core.service.SigninUserService;

/**
 * @author singo
 */
@Service("signinUserService")
public class SigninUserServiceImpl implements SigninUserService {

    @Value("${signin.timeout}")
    private int                               timeout;                          // 单位：分钟

    private static final String               SIGNIN_REDIS_KEY_PREX = "signin.";

    @Autowired
    private RedisTemplate<String, SigninUser> redisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<SigninUser>(SigninUser.class));
    }

    @Override
    public SigninUser get(String uk) {

        Assert.hasText(uk);

        SigninUser signinUser = redisTemplate.opsForValue().get(redisKey(uk));
        if (signinUser == null) {
            return null;
        }

        // 登录回话时间自动延长
        put(uk, signinUser);
        return signinUser;
    }

    @Override
    public void put(String uk, SigninUser signinUser) {
        Assert.hasText(uk);
        Assert.notNull(signinUser);
        redisTemplate.opsForValue().set(redisKey(uk), signinUser, timeout * 60 * 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void remove(String uk) {
        Assert.hasText(uk);
        redisTemplate.delete(redisKey(uk));
    }

    private String redisKey(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return SIGNIN_REDIS_KEY_PREX + val;
    }
}
