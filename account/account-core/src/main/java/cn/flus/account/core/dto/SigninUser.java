package cn.flus.account.core.dto;

import java.io.Serializable;

/**
 * 登录的用户
 * 
 * @author singo
 */
public class SigninUser implements Serializable {

    private static final long serialVersionUID = 5562136997706961025L;

    private Integer           id;
    private String            loginname;
    private String            nickname;

    private String            email;
    private String            mobile;

    public SigninUser() {
    }

    public SigninUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public String toString() {
        return "SigninUser [id=" + id + ", loginname=" + loginname + "]";
    }
}
