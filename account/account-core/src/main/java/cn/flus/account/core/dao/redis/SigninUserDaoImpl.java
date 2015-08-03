package cn.flus.account.core.dao.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.SigninUserDao;
import cn.flus.account.core.dto.SigninUser;

@Service("signinUserDao")
public class SigninUserDaoImpl extends AbstractBaseRedisDao<String, SigninUser> implements SigninUserDao {

    @Override
    public Boolean put(final String uk, final SigninUser signinUser) {
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(uk);
                byte[] name = serializer.serialize(signinUser.getId().toString());
                return connection.setNX(key, name);
            }
        });
        return result;
    }

    @Override
    public SigninUser get(final String uk) {
        SigninUser result = redisTemplate.execute(new RedisCallback<SigninUser>() {

            public SigninUser doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(uk);
                if (key == null) {
                    return null;
                }
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
                return new SigninUser(Integer.parseInt(name));
            }
        });
        return result;
    }
}
