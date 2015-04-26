package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.web.domain.LoginUser;

@Service("loginContextStorageFilter")
public class LoginContextStorageFilter extends GenericFilterBean {

    private static final String       FILTER_APPLIED     = LoginContextStorageFilter.class.getName() + ".applied";

    private static LoginContextHolder loginContextHolder = new LoginContextHolder();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return;
        }
        httpRequest.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        // TODO
        LoginUser loginUser = new LoginUser();

        try {
            loginContextHolder.setContext(loginUser);
            chain.doFilter(request, response);
        } finally {
            loginContextHolder.clearContext();
            httpRequest.removeAttribute(FILTER_APPLIED);
        }
    }
}
