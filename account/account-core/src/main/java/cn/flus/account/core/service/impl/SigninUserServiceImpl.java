package cn.flus.account.core.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

        // 获取登录的用户信息
        if (StringUtils.isBlank(uk)) {
            return null;
        }
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
        signinUserDao.put(uk, signinUser, timeout * 60 * 1000);
    }
}
