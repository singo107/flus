package cn.flus.account.core.entity;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author zhoux 2013-10-18
 */
public class AccountUserEntity implements Serializable {

    private static final long serialVersionUID = 5003984711125896241L;

    private Integer           id;
    private String            email;
    private String            mobile;
    private String            password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
