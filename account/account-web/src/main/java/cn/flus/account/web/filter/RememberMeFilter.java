package cn.flus.account.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

@Service("rememberMeFilter")
public class RememberMeFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        chain.doFilter(request, response);
    }

}
