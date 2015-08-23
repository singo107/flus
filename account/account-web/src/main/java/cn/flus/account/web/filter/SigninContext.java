package cn.flus.account.web.filter;

import cn.flus.account.core.dto.SigninUser;

public class SigninContext {

    // 当前访问是否需要登录保护
    private boolean    signinRequired;

    // 当期登录的用户
    private SigninUser signinUser;

    public boolean isSigninRequired() {
        return signinRequired;
    }

    public void setSigninRequired(boolean signinRequired) {
        this.signinRequired = signinRequired;
    }

    public SigninUser getSigninUser() {
        return signinUser;
    }

    public void setSigninUser(SigninUser signinUser) {
        this.signinUser = signinUser;
    }

}
