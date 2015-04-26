package cn.flus.account.web.filter;

import cn.flus.account.web.domain.LoginUser;

public class LoginContextHolder {

    private static final ThreadLocal<LoginUser> holder = new ThreadLocal<LoginUser>();

    public void clearContext() {
        holder.remove();
    }

    public LoginUser getContext() {
        LoginUser loginUser = holder.get();
        if (loginUser == null) {
            loginUser = new LoginUser();
            holder.set(loginUser);
        }
        return loginUser;
    }

    public void setContext(LoginUser loginUser) {
        holder.set(loginUser);
    }

}
