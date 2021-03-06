package cn.flus.account.web.filter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import cn.flus.account.web.utils.UrlUtils;

/**
 * 统一的Filter入口，用于登录认证
 * 
 * @author singo 2014-09-14
 */
@Service("accountFilterChain")
public class AccountFilterChain extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(AccountFilterChain.class);

    @Resource(name = "accountFilterList")
    private List<Filter>        accountFilterList;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (accountFilterList == null || accountFilterList.size() == 0) {
            logger.debug(UrlUtils.buildRequestUrl(httpRequest) + " has an empty filter list.");
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
        VirtualFilterChain vfc = new VirtualFilterChain(chain, accountFilterList);
        vfc.doFilter(httpRequest, httpResponse);
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
