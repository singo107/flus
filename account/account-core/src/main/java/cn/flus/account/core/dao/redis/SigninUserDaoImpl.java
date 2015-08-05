package cn.flus.account.core.dao.redis;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.SigninUserDao;
import cn.flus.account.core.dto.SigninUser;

@Service("signinUserDao")
public class SigninUserDaoImpl implements SigninUserDao {

    @Autowired
    private RedisTemplate<String, SigninUser>   template;

    private ValueOperations<String, SigninUser> operations;

    // uk在redis中的key的前缀
    private static final String                 SIGNIN_REDIS_KEY_PREX = "uk.";

    @PostConstruct
    public void init() {
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(SigninUser.class));
        operations = template.opsForValue();
    }

    @Override
    public void put(final String uk, final SigninUser signinUser) {
        operations.set(generateRedisKey(uk), signinUser);
    }

    @Override
    public SigninUser get(final String uk) {
        if (StringUtils.isBlank(generateRedisKey(uk))) {
            return null;
        }
        return operations.get(generateRedisKey(uk));
    }

    /**
     * 生成redis的key
     * 
     * @param uk
     * @return
     */
    private String generateRedisKey(String uk) {
        if (StringUtils.isBlank(uk)) {
            return null;
        }
        return SIGNIN_REDIS_KEY_PREX + uk;
    }
}
