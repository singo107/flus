package cn.flus.account.core.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.dao.mapper.AccountUserEntityMapper;
import cn.flus.account.core.enums.AccountStatus;
import cn.flus.account.core.enums.VerifyTag;
import cn.flus.account.core.exceptions.ExceedMaxValidateException;
import cn.flus.account.core.exceptions.LoginnameExistException;
import cn.flus.account.core.exceptions.LoginnameInvalidException;
import cn.flus.account.core.exceptions.LoginnameNotFoundException;
import cn.flus.account.core.exceptions.PasswordInvalidException;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.core.utils.LoginnameUtils;
import cn.flus.account.core.utils.PasswordStrength;

/**
 * @author singo
 */
@Service("accountUserService")
public class AccountUserServiceImpl implements AccountUserService {

    private static final Logger           logger                        = LoggerFactory.getLogger(AccountUserServiceImpl.class);

    @Autowired
    private AccountUserEntityMapper       accountUserEntityMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 30分钟之内，密码只能错3次
    private static final String           PASSWORD_CHECK_REDIS_KEY_PREX = "password.check.";
    private final static int              PASSWORD_CHECK_EXPIRE_TIME    = 30 * 60 * 1000;                                       // 30分钟
    private final static int              PASSWORD_CHECK_ERROR_TIMES    = 3;

    @PostConstruct
    public void init() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    @Override
    public AccountUserEntity getById(Integer id) {
        return accountUserEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public AccountUserEntity getByLoginname(String loginname) {
        return accountUserEntityMapper.selectByLoginname(loginname);
    }

    @Override
    public AccountUserEntity signup(String loginname, String password) {

        Assert.hasText(loginname);
        Assert.hasText(password);

        // loginname校验
        if (!LoginnameUtils.check(loginname)) {
            throw new LoginnameInvalidException("loginname is invalid.");
        }

        // password校验
        int strength = PasswordStrength.calStrength(password);
        if (strength < PasswordStrength.DEFAULT_VALID_STRENGTH) {
            throw new PasswordInvalidException("password is invalid.");
        }

        // loginname是否已经占用
        boolean isExist = checkExist(loginname);
        if (isExist) {
            throw new LoginnameExistException("loginname is exist.");
        }

        // 生成密码盐
        String passwordSalt = generateSalt();

        // 密码和密码盐散列
        String passwordMd5Hex = DigestUtils.md5Hex(password + passwordSalt);

        // 开始初始化数据
        AccountUserEntity entity = new AccountUserEntity();
        entity.setLoginname(loginname);
        entity.setPassword(passwordMd5Hex);
        entity.setPasswordSalt(passwordSalt);
        entity.setPasswordStrength(strength);
        entity.setStatus(AccountStatus.AVAILABLE.getCode());
        entity.setRegisterTime(new Date());

        // 如果用户名是Email，则设置email
        if (LoginnameUtils.isEmail(loginname)) {
            entity.setEmail(loginname);
            entity.setEmailVerified(VerifyTag.NOT_VERIFIED.getCode());
        }

        // 如果用户名是Mobile，则设置mobile
        if (LoginnameUtils.isMobile(loginname)) {
            entity.setMobile(loginname);
            entity.setMobileVerified(VerifyTag.NOT_VERIFIED.getCode());
        }

        // 添加到数据库
        accountUserEntityMapper.insert(entity);
        return entity;
    }

    @Override
    public boolean checkExist(String loginname) {

        Assert.hasText(loginname);

        // loginname校验
        if (!LoginnameUtils.check(loginname)) {
            throw new LoginnameInvalidException("loginname is invalid.");
        }

        // 如果用户名已被占用，直接返回为true
        if (accountUserEntityMapper.selectByLoginname(loginname) != null) {
            return true;
        }

        // 如果用户名是email，还需检查email是否已经占用
        if (LoginnameUtils.isEmail(loginname)) {
            if (accountUserEntityMapper.selectByEmail(loginname) != null) {
                return true;
            }
        }

        // 如果用户名是mobile，还需检查mobile是否已经占用
        if (LoginnameUtils.isMobile(loginname)) {
            if (accountUserEntityMapper.selectByMobile(loginname) != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkPassword(String loginname, String password) {

        // 判断用户名是否存在
        AccountUserEntity entity = getByLoginname(loginname);
        if (entity == null) {
            throw new LoginnameNotFoundException("loginname is not exist.");
        }
        return checkPassword(entity, password);
    }

    @Override
    public boolean checkPassword(AccountUserEntity entity, String password) {

        Assert.notNull(entity);
        Assert.hasText(password);
        Assert.hasText(entity.getPassword());
        Assert.hasText(entity.getPasswordSalt());

        // 判断密码校验错误的次数
        String strTimes = redisTemplate.opsForValue().get(redisKey(entity.getId().toString()));
        int times = 0;
        if (StringUtils.isNotBlank(strTimes)) {
            times = Integer.parseInt(strTimes);
            if (times >= PASSWORD_CHECK_ERROR_TIMES) {
                logger.warn("login password error times exceed max: " + PASSWORD_CHECK_ERROR_TIMES);
                throw new ExceedMaxValidateException("login password error times exceed max: "
                                                     + PASSWORD_CHECK_ERROR_TIMES);
            }
        }

        // 使用密码盐与参数password作Hash运算
        String passwordMd5Hex = DigestUtils.md5Hex(password + entity.getPasswordSalt());

        // 校验
        if (passwordMd5Hex.equals(entity.getPassword())) {

            redisTemplate.delete(redisKey(entity.getId().toString()));
            return true;

        } else {

            // 密码错误次数加1
            redisTemplate.opsForValue().set(redisKey(entity.getId().toString()), Integer.toString(++times),
                                            PASSWORD_CHECK_EXPIRE_TIME, TimeUnit.MILLISECONDS);
            return false;
        }
    }

    /**
     * 生成密码盐
     * 
     * @return
     */
    private String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    private String redisKey(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return PASSWORD_CHECK_REDIS_KEY_PREX + val;
    }
}
