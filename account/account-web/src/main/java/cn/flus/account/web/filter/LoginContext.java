package cn.flus.account.web.filter;

import cn.flus.account.web.domain.LoginUser;

public class LoginContext {

    private LoginUser loginUser;

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

}
