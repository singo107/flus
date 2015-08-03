package cn.flus.account.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.SigninUserDao;
import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.core.service.SigninUserService;

/**
 * @author singo
 */
@Service("signinUserService")
public class SigninUserServiceImpl implements SigninUserService {

    @Autowired
    private SigninUserDao signinUserDao;

    @Override
    public SigninUser get(String uk) {
        return signinUserDao.get(uk);
    }

    @Override
    public void put(String uk, SigninUser signinUser) {
        signinUserDao.put(uk, signinUser);
    }
}
