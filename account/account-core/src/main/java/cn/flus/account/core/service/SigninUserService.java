package cn.flus.account.core.service;

import cn.flus.account.core.dto.SigninUser;

/**
 * 登录用户
 * 
 * @author singo
 */
public interface SigninUserService {

    /**
     * 获取登录用户
     * 
     * @param uk
     * @return
     */
    SigninUser get(String uk);

    /**
     * 暂存登录用户
     * 
     * @param uk
     * @param signinUser
     */
    void put(String uk, SigninUser signinUser);

    /**
     * 删除登录用户
     * 
     * @param uk
     */
    void remove(String uk);
}
