package cn.flus.account.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.flus.account.core.dao.SigninUserDao;
import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.core.service.SigninUserService;

/**
 * @author singo
 */
@Service("signinUserService")
public class SigninUserServiceImpl implements SigninUserService {

    @Value("${signin.timeout}")
    private int           timeout;      // 单位：分钟

    @Autowired
    private SigninUserDao signinUserDao;

    @Override
    public SigninUser get(String uk) {

        Assert.hasText(uk);
        SigninUser signinUser = signinUserDao.get(uk);
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
        signinUserDao.put(uk, signinUser, timeout * 60 * 1000);
    }

    @Override
    public void remove(String uk) {
        Assert.hasText(uk);
        signinUserDao.remove(uk);
    }
}
