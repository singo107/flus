package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.web.domain.LoginUser;
import cn.flus.account.web.utils.CookieUtils;

@Service("loginContextStorageFilter")
public class LoginContextStorageFilter extends GenericFilterBean {

    private static final Logger logger              = LoggerFactory.getLogger(LoginContextStorageFilter.class);

    private static final String SESSION_KEY_CK_NAME = "sn_k";

    private static final String FILTER_APPLIED      = LoginContextStorageFilter.class.getName() + ".applied";

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
        LoginUser loginUser = getFromSession(httpRequest);
        LoginContext loginContext = new LoginContext();
        loginContext.setLoginUser(loginUser);

        try {
            LoginContextHolder.setContext(loginContext);
            chain.doFilter(request, response);
        } finally {
            LoginContextHolder.clearContext();
            httpRequest.removeAttribute(FILTER_APPLIED);
        }
    }

    /**
     * 从Session中获取当前登录用户的信息
     * 
     * @param httpRequest
     * @return
     */
    private LoginUser getFromSession(HttpServletRequest httpRequest) {

        // 从request获取cookie
        String cookieValue = CookieUtils.getValue(httpRequest, SESSION_KEY_CK_NAME);
        logger.debug("" + cookieValue);

        Object o = httpRequest.getSession().getAttribute("cuser");
        if (o == null) {
            return null;
        }
        if (!(o instanceof LoginUser)) {
            return null;
        }
        return (LoginUser) o;
    }
}
