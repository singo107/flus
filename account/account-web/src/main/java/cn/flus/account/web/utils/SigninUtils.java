package cn.flus.account.web.utils;

import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.web.filter.SigninContextHolder;

/**
 * 获取当前登录的用户
 * 
 * @author singo
 */
public class SigninUtils {

    /**
     * 获取当期登录的用户信息
     * 
     * @return
     */
    public static SigninUser get() {
        return SigninContextHolder.getContext().getSigninUser();
    }
}
