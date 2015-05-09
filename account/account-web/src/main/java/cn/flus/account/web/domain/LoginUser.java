package cn.flus.account.web.domain;

/**
 * 存放在Session中的用户信息
 * 
 * @author singo
 */
public class LoginUser {

    private Integer id;
    private String  loginname;
    private String  nickname;

    private String  email;
    private String  mobile;

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
        return "LoginUser [id=" + id + ", loginname=" + loginname + ", nickname=" + nickname + ", email=" + email
               + ", mobile=" + mobile + "]";
    }
}
