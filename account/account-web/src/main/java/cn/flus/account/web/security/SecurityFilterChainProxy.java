package cn.flus.account.web.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

@Service("securityFilterChain")
public class SecurityFilterChainProxy extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取filter列表
        List<Filter> filters = getFilters(httpRequest);
        if (filters == null || filters.size() == 0) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        VirtualFilterChain vfc = new VirtualFilterChain(chain, filters);
        vfc.doFilter(httpRequest, httpResponse);
    }

    private List<Filter> getFilters(HttpServletRequest request) {
        return null;
    }

    private static class VirtualFilterChain implements FilterChain {

        private final FilterChain  originalChain;
        private final List<Filter> additionalFilters;
        private final int          size;
        private int                currentPosition = 0;

        private VirtualFilterChain(FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
        }

        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (currentPosition == size) {
                originalChain.doFilter(request, response);
            } else {
                currentPosition++;
                Filter nextFilter = additionalFilters.get(currentPosition - 1);
                nextFilter.doFilter(request, response, this);
            }
        }
    }
}
