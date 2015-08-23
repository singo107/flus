package cn.flus.account.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.core.dto.SigninUser;
import cn.flus.account.web.utils.SigninUtils;
import cn.flus.account.web.utils.UrlUtils;

@Service("signinCheckFilter")
public class SigninCheckFilter extends GenericFilterBean {

    @Value("${path.login}")
    private String loginPath;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 不受登录保护的请求
        if (!SigninContextHolder.getContext().isSigninRequired()) {
            chain.doFilter(request, response);
            return;
        }

        // 如果用户已经登录
        SigninUser signinUser = SigninUtils.get();
        if (signinUser != null) {
            chain.doFilter(request, response);
            return;
        }

        // 跳转到登录页面，并把目标地址传递给登录页面
        String destUrl = URLEncoder.encode(UrlUtils.buildFullRequestUrl(httpRequest), "utf-8");
        httpResponse.sendRedirect(loginPath + "?dest=" + destUrl);
    }
}
