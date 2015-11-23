package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.core.dao.domain.AccountUserEntity;
import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.web.utils.SignExecutor;
import cn.flus.account.web.utils.SigninUtils;

@Service("rememberMeFilter")
public class RememberMeFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(RememberMeFilter.class);

    @Autowired
    private SignExecutor        signinExecutor;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 如果用户已经登录
        SigninUser signinUser = SigninUtils.get();
        if (signinUser != null) {
            chain.doFilter(request, response);
            return;
        }

        // 根据cookie获取记住的用户信息
        AccountUserEntity accountUserEntity = signinExecutor.getUserFromRememberMe(httpRequest);
        if (accountUserEntity == null) {
            chain.doFilter(request, response);
            return;
        }

        // 登录
        signinUser = signinExecutor.signin(accountUserEntity, httpResponse);
        logger.info("Remember me worked, user: " + signinUser);
        SigninContextHolder.getContext().setSigninUser(signinUser);
        chain.doFilter(request, response);
    }
}
