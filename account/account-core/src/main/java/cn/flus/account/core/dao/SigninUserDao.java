package cn.flus.account.core.dao;

import cn.flus.account.core.dto.SigninUser;

public interface SigninUserDao {

    Boolean put(String uk, SigninUser signinUser);

    SigninUser get(String uk);
}
