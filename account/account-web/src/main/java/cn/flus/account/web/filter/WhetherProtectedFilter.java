package cn.flus.account.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.web.config.RequestPattern;
import cn.flus.account.web.config.UnprotectedConfig;

@Service("whetherProtectedFilter")
public class WhetherProtectedFilter extends GenericFilterBean {

    @Autowired
    private UnprotectedConfig           unprotectedConfig;

    private static List<RequestMatcher> unprotectedRequestMatchers;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        // 检查当前页面是否需要被登录保护
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean unprotected = check(httpRequest);
        SigninContextHolder.getContext().setNeedProtected(!unprotected);
        chain.doFilter(request, response);
    }

    /**
     * 判断当前请求是否不受登录保护
     * 
     * @param httpRequest
     * @return
     */
    private boolean check(HttpServletRequest httpRequest) {
        for (RequestMatcher e : unprotectedRequestMatchers) {
            if (e.matches(httpRequest)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        if (unprotectedConfig != null && unprotectedConfig.getUnprotected() != null
            && unprotectedConfig.getUnprotected().size() > 0) {
            RequestMatcher requestMatcher = null;
            unprotectedRequestMatchers = new ArrayList<RequestMatcher>();
            for (RequestPattern p : unprotectedConfig.getUnprotected()) {
                if (p.getHttpMethod() != null && p.getHttpMethod().length() > 0) {
                    requestMatcher = new RequestMatcher(p.getPattern(), p.getHttpMethod());
                } else {
                    requestMatcher = new RequestMatcher(p.getPattern());
                }
                unprotectedRequestMatchers.add(requestMatcher);
            }
        }
    }

}