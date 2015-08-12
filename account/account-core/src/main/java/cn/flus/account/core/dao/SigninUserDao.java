package cn.flus.account.core.dao;

import cn.flus.account.core.dto.SigninUser;

public interface SigninUserDao {

    void put(String key, SigninUser signinUser, long timeout);

    SigninUser get(String key);

    void remove(String key);
}
