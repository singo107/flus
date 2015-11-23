package cn.flus.account.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.core.service.SigninUserService;

/**
 * 登录
 * 
 * @author singo
 */
@Service("signinExecutor")
public class SignExecutor {

    private static final Logger logger              = LoggerFactory.getLogger(SignExecutor.class);

    // 记录用户登录的唯一键，存放在Cookie中
    private static final String SIGNIN_USER_CK      = "sn_k";

    // 记录用户记住登录的唯一键，存放在Cookie中
    private static final String REMEMBER_ME_CK_NAME = "rmb_k";

    @Value("${signin.domain}")
    private String              signinDomain;

    @Autowired
    private SigninUserService   signinUserService;

    @Autowired
    private AccountUserService  accountUserService;

    /**
     * 用户登录
     * 
     * @param u
     * @param response
     * @return
     */
    public SigninUser signin(AccountUserEntity u, HttpServletResponse response) {

        // 拼装登录的用户信息
        SigninUser signinUser = new SigninUser();
        signinUser.setId(u.getId());
        signinUser.setLoginname(u.getLoginname());
        signinUser.setNickname(u.getNickname());
        signinUser.setEmail(u.getEmail());
        signinUser.setMobile(u.getMobile());

        // 生成键值
        String uk = UIdUtils.generate();

        // 持久化登录用户
        signinUserService.put(uk, signinUser);

        // 设置cookie
        Cookie signinCookie = new Cookie(SIGNIN_USER_CK, uk);
        signinCookie.setDomain(signinDomain);
        signinCookie.setPath("/");
        response.addCookie(signinCookie);
        logger.info(signinUser + ", has signin.");
        return signinUser;
    }

    /**
     * 用户登出
     * 
     * @param request
     * @param response
     */
    public void signinOut(HttpServletRequest request, HttpServletResponse response) {

        // 获取UK
        String uk = CookieUtils.getValue(request, SIGNIN_USER_CK);
        if (StringUtils.isBlank(uk)) {
            return;
        }

        // 删除服务端存储的用户信息
        signinUserService.remove(uk);

        // 清除登录的cookie
        Cookie signinCookie = new Cookie(SIGNIN_USER_CK, null);
        signinCookie.setDomain(signinDomain);
        signinCookie.setPath("/");
        signinCookie.setMaxAge(0);
        response.addCookie(signinCookie);

        // 清除记住登录的cookie
        Cookie rememberMeCookie = new Cookie(REMEMBER_ME_CK_NAME, null);
        rememberMeCookie.setDomain(signinDomain);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setMaxAge(0);
        response.addCookie(rememberMeCookie);
    }

    /**
     * 获取已经登录用户的信息
     * 
     * @param httpRequest
     * @return
     */
    public SigninUser getSignin(HttpServletRequest request) {
        String uk = CookieUtils.getValue(request, SIGNIN_USER_CK);
        if (StringUtils.isBlank(uk)) {
            return null;
        }
        return signinUserService.get(uk);
    }

    /**
     * 记住用户登录
     * 
     * @param user
     * @param response
     * @param days
     */
    public void rememberMe(AccountUserEntity user, HttpServletResponse response, int days) {

        // 计算签名
        String signature = makeTokenSignature(user.getId(), user.getPassword());

        // 拼装cookie
        StringBuilder sb = new StringBuilder();
        sb.append(user.getId());
        sb.append(":");
        sb.append(signature);
        String cookieValue = new String(Base64.encodeBase64(sb.toString().getBytes()));

        // 设置cookie
        Cookie rememberMeCookie = new Cookie(REMEMBER_ME_CK_NAME, cookieValue);
        rememberMeCookie.setDomain(signinDomain);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setMaxAge(days * 24 * 3600);
        response.addCookie(rememberMeCookie);
    }

    /**
     * 从记住登录的ck中获取用户实体
     * 
     * @param request
     * @return
     */
    public AccountUserEntity getUserFromRememberMe(HttpServletRequest request) {

        // 从request获取cookie
        String cookieValue = CookieUtils.getValue(request, REMEMBER_ME_CK_NAME);
        String[] cookieTokens = parseCookie(cookieValue);
        if (cookieTokens == null) {
            return null;
        }

        // 从数据库中获取当前用户
        AccountUserEntity user = accountUserService.getById(Integer.parseInt(cookieTokens[0]));
        if (user == null) {
            logger.warn("remember me cookie invalid: user(" + cookieTokens[0] + ") is null.");
            return null;
        }

        // 验签
        String expectedTokenSignature = makeTokenSignature(user.getId(), user.getPassword());
        if (!cookieTokens[1].equals(expectedTokenSignature)) {
            logger.warn("remember me cookie invalid: user(" + cookieTokens[0] + ") signature error.");
            return null;
        }

        return user;
    }

    private String makeTokenSignature(int id, String password) {
        String data = id + ":" + password;
        return DigestUtils.md5Hex(data.getBytes());
    }

    /**
     * 解析记住我的cookie
     * 
     * @param cookieValue
     * @return
     */
    private String[] parseCookie(String cookieValue) {
        if (cookieValue == null || cookieValue.length() <= 0) {
            return null;
        }
        String cookieAsPlainText = new String(Base64.decodeBase64(cookieValue));
        if (cookieAsPlainText == null || cookieAsPlainText.length() <= 0) {
            return null;
        }
        String[] cookieTokens = cookieAsPlainText.split(":");
        if (cookieTokens == null || cookieTokens.length != 2) {
            return null;
        }
        return cookieTokens;
    }
}
