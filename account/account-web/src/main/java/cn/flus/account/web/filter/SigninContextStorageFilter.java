package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.web.utils.SignExecutor;

/**
 * 把登录的用户信息放在在ThreadLocal中保存
 * 
 * @author singo
 */
@Service("signinContextStorageFilter")
public class SigninContextStorageFilter extends GenericFilterBean {

    private static final String FILTER_APPLIED = SigninContextStorageFilter.class.getName() + ".applied";

    @Autowired
    private SignExecutor        signinExecutor;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return;
        }
        httpRequest.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        // 获取当前已经登录的用户信息
        SigninUser signinUser = signinExecutor.getSignin(httpRequest);

        // 建立AccountFilterChain的上下文
        SigninContext context = new SigninContext();
        context.setSigninUser(signinUser);
        try {
            SigninContextHolder.setContext(context);
            chain.doFilter(request, response);
        } finally {
            SigninContextHolder.clearContext();
            httpRequest.removeAttribute(FILTER_APPLIED);
        }
    }
}
