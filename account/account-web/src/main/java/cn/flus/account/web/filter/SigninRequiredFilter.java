package cn.flus.account.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 判断请求是否需要登录
 * 
 * @author singo
 */
@Service("signinRequiredFilter")
public class SigninRequiredFilter extends GenericFilterBean {

    private static final Logger         logger   = LoggerFactory.getLogger(SigninRequiredFilter.class);

    private static final String         XML_FILE = "signin-required.xml";

    private static List<SigninRequired> list;

    @Override
    protected void initFilterBean() throws ServletException {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(XML_FILE);
            SigninRequiredReader handler = new SigninRequiredReader();
            SAXParserFactory.newInstance().newSAXParser().parse(inputStream, handler);
            list = handler.get();
        } catch (Exception e) {
            logger.error("read xml failed, xml=" + XML_FILE, e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        SigninContextHolder.getContext().setSigninRequired(check((HttpServletRequest) request));
        chain.doFilter(request, response);
    }

    /**
     * 判断是否需要登录保护
     * 
     * @param httpRequest
     * @return
     */
    private boolean check(HttpServletRequest httpRequest) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        RequestMatcher matcher = null;
        for (SigninRequired e : list) {
            matcher = new RequestMatcher(e.getPattern(), e.getMethod().toUpperCase());
            if (matcher.matches(httpRequest)) {
                return e.isRequired();
            }
        }
        return true;
    }
}
