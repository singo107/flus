package cn.flus.account.web.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.web.config.RequestPattern;
import cn.flus.account.web.config.UnprotectedConfig;
import cn.flus.account.web.utils.UrlUtils;

@Service("redirect2LoginFilter")
public class Redirect2LoginFilter extends GenericFilterBean {

    @Autowired
    private UnprotectedConfig           unprotectedConfig;
    private static List<RequestMatcher> unprotectedRequestMatchers;

    @Value("${path.login}")
    private String                      loginPath;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 不受登录保护的页面，直接跳过
        boolean unprotected = check(httpRequest);
        if (unprotected) {
            chain.doFilter(request, response);
            return;
        }

        // 如果用户没有登录
        if (LoginContextHolder.getContext().getLoginUser() == null) {

            // 读取当前请求的URL，便于登录成功后返回
            String destUrl = URLEncoder.encode(UrlUtils.buildFullRequestUrl(httpRequest), "utf-8");

            // 拼装登录的url
            String loginUrl = loginPath + "?dest=" + destUrl;
            httpResponse.sendRedirect(loginUrl);
            return;
        }

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
