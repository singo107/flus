package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.service.AccountUserService;
import cn.flus.account.web.domain.LoginUser;
import cn.flus.account.web.utils.CookieUtils;
import cn.flus.core.utils.DigestUtils;

@Service("rememberMeFilter")
public class RememberMeFilter extends GenericFilterBean {

    private static final Logger logger              = LoggerFactory.getLogger(RememberMeFilter.class);

    private static final String REMEMBER_ME_CK_NAME = "rmb_k";

    @Autowired
    private AccountUserService  accountUserService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (LoginContextHolder.getContext().getLoginUser() == null) {

            // 根据cookie获取记住登录的用户信息
            LoginUser loginUser = autoLogin(httpRequest);
            if (loginUser != null) {

                LoginContextHolder.getContext().setLoginUser(loginUser);

                // 保存Session
                httpRequest.getSession().setAttribute("cuser", loginUser);
            }
        }

        chain.doFilter(request, response);
    }

    private LoginUser autoLogin(HttpServletRequest httpRequest) {

        // 从request获取cookie
        String cookieValue = CookieUtils.getValue(httpRequest, REMEMBER_ME_CK_NAME);
        String[] cookieTokens = parseCookie(cookieValue);
        if (cookieTokens == null) {
            return null;
        }

        // 检查cookie是否已经到期
        long tokenExpiryTime = new Long(cookieTokens[1]).longValue();
        if (tokenExpiryTime < System.currentTimeMillis()) {
            return null;
        }

        // 从数据库中获取当前用户
        AccountUserEntity u = accountUserService.getById(Integer.getInteger(cookieTokens[0]));
        if (u == null) {
            logger.warn("remember me cookie invalid: user(" + cookieTokens[0] + ") is null.");
            return null;
        }

        // 验签
        String expectedTokenSignature = makeTokenSignature(tokenExpiryTime, u.getId(), u.getPassword());
        if (!cookieTokens[2].equals(expectedTokenSignature)) {
            logger.warn("remember me cookie invalid: user(" + cookieTokens[0] + ") signature error.");
            return null;
        }

        // 返回登录的用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setId(u.getId());
        loginUser.setLoginname(u.getLoginname());
        loginUser.setEmail(u.getEmail());
        loginUser.setMobile(u.getMobile());
        loginUser.setNickname(u.getNickname());
        return loginUser;
    }

    private String makeTokenSignature(long tokenExpiryTime, int id, String password) {
        String data = id + ":" + tokenExpiryTime + ":" + password;
        return DigestUtils.encrypt(data);
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
        if (cookieTokens == null || cookieTokens.length != 3) {
            return null;
        }
        return cookieTokens;
    }
}
