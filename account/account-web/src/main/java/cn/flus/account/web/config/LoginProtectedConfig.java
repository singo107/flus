package cn.flus.account.web.config;

import java.util.List;

public class LoginProtectedConfig {

    private List<RequestPattern> protectedList;
    private List<RequestPattern> unprotectedList;

    public List<RequestPattern> getProtectedList() {
        return protectedList;
    }

    public void setProtectedList(List<RequestPattern> protectedList) {
        this.protectedList = protectedList;
    }

    public List<RequestPattern> getUnprotectedList() {
        return unprotectedList;
    }

    public void setUnprotectedList(List<RequestPattern> unprotectedList) {
        this.unprotectedList = unprotectedList;
    }

}
