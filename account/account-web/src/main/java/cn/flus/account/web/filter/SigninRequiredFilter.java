package cn.flus.account.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 判断请求是否需要登录
 * 
 * @author singo
 */
@Service("signinRequiredFilter")
public class SigninRequiredFilter extends GenericFilterBean {

    private static List<RequestMatcher> unrequiredList;

    @Override
    protected void initFilterBean() throws ServletException {
        // 初始化登录配置
        unrequiredList = new ArrayList<RequestMatcher>();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            SigninRequiredReader handler = new SigninRequiredReader("http");
            parser.parse(this.getClass().getClassLoader().getResourceAsStream("signin-required.xml"), handler);
            unrequiredList = handler.getUnrequiredList();
        } catch (Exception e) {
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        SigninContextHolder.getContext().setSigninRequired(check((HttpServletRequest) request));
        chain.doFilter(request, response);
    }

    /**
     * 判断当前请求是否需要登录
     * 
     * @param httpRequest
     * @return
     */
    private boolean check(HttpServletRequest httpRequest) {
        if (unrequiredList == null || unrequiredList.size() <= 0) {
            return true;
        }
        for (RequestMatcher e : unrequiredList) {
            if (e.matches(httpRequest)) {
                return false;
            }
        }
        return true;
    }
}
