package cn.flus.account.web.utils;

import cn.flus.account.web.domain.LoginUser;
import cn.flus.account.web.filter.LoginContextHolder;

public class LoginUserUtil {

    public static LoginUser get() {
        return LoginContextHolder.getContext().getLoginUser();
    }

}
