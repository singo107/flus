package cn.flus.account.core.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.dao.AccountUserDao;
import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.enums.AccountStatus;
import cn.flus.account.core.enums.AccountType;
import cn.flus.account.core.enums.LoginnameType;
import cn.flus.account.core.exceptions.LoginnameExistException;
import cn.flus.account.core.exceptions.LoginnameInvalidException;
import cn.flus.account.core.exceptions.PasswordInvalidException;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.core.utils.PasswordStrength;

/**
 * @author zhouxing
 */
@Service("accountUserService")
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserDao accountUserDao;

    @Override
    public AccountUserEntity getById(Integer id) {
        return accountUserDao.get(id);
    }

    @Override
    public AccountUserEntity getByLoginname(String loginname) {
        return accountUserDao.getByLoginname(loginname);
    }

    @Override
    public AccountUserEntity signup(String loginname, String password) {

        Assert.hasText(loginname);
        Assert.hasText(password);

        // loginname校验
        LoginnameType loginnameType = LoginnameType.getByLoginname(loginname);
        if (loginnameType == null) {
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
        entity.setType(AccountType.NORMAL.getCode());
        entity.setLevel(1);
        entity.setValidate(0);
        entity.setCreateTime(new Date());

        // 如果用户名是Email，则设置email
        if (LoginnameType.EMAIL.equals(loginnameType)) {
            entity.setEmail(loginname);
        }

        // 如果用户名是Mobile，则设置mobile
        if (LoginnameType.MOBILE.equals(loginnameType)) {
            entity.setMobile(loginname);
        }

        // 添加到数据库
        Integer id = accountUserDao.insert(entity);

        // 返回对象
        entity.setId(id);
        return entity;
    }

    @Override
    public boolean checkExist(String loginname) {

        Assert.hasText(loginname);

        // loginname校验
        LoginnameType loginnameType = LoginnameType.getByLoginname(loginname);
        if (loginnameType == null) {
            throw new LoginnameInvalidException("loginname is invalid.");
        }

        // 如果用户名已被占用，直接返回为true
        if (accountUserDao.getByLoginname(loginname) != null) {
            return true;
        }

        // 如果用户名是email，还需检查email是否已经占用
        if (LoginnameType.EMAIL.equals(loginnameType)) {
            if (accountUserDao.getByEmail(loginname) != null) {
                return true;
            }
        }

        // 如果用户名是mobile，还需检查mobile是否已经占用
        if (LoginnameType.MOBILE.equals(loginnameType)) {
            if (accountUserDao.getByMobile(loginname) != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * 生成密码盐
     * 
     * @return
     */
    private String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(16);
    }
}
