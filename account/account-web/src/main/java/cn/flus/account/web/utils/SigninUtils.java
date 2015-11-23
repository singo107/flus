package cn.flus.account.web.utils;

import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.web.filter.SigninContextHolder;

/**
 * 当前登录的用户的工具类
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

    /**
     * 判断用户是否已经登录成功
     * 
     * @return
     */
    public static boolean isSignin() {
        return (get() != null);
    }

}
