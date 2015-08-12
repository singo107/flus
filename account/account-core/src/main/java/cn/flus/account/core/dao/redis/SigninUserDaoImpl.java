package cn.flus.account.core.dao.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.SigninUserDao;
import cn.flus.account.core.dto.SigninUser;

@Service("signinUserDao")
public class SigninUserDaoImpl extends RedisKeyManagement implements SigninUserDao {

    @Autowired
    private RedisTemplate<String, SigninUser>   template;

    private ValueOperations<String, SigninUser> operations;

    @PostConstruct
    public void init() {
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(SigninUser.class));
        operations = template.opsForValue();
    }

    public void put(final String key, final SigninUser signinUser, long timeout) {
        operations.set(signinKey(key), signinUser, timeout, TimeUnit.MILLISECONDS);
    }

    public SigninUser get(final String key) {
        return operations.get(signinKey(key));
    }

    public void remove(final String key) {
        template.delete(signinKey(key));
    }

}
