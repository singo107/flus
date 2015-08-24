package cn.flus.account.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * 请求匹配器
 * 
 * @author singo 2015-04-18
 */
public class RequestMatcher {

    private static final Logger logger    = LoggerFactory.getLogger(RequestMatcher.class);
    private static final String MATCH_ALL = "/**";

    private final Matcher       matcher;
    private final String        pattern;
    private final HttpMethod    httpMethod;

    public RequestMatcher(String pattern) {
        this(pattern, HttpMethod.GET.toString());
    }

    public RequestMatcher(String pattern, String httpMethod) {

        logger.debug("pattern: " + pattern + ", httpMethod: " + httpMethod);

        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            matcher = null;
        } else {
            if (pattern.endsWith(MATCH_ALL) && pattern.indexOf('?') == -1
                && pattern.indexOf("*") == pattern.length() - 2) {
                matcher = new SubpathMatcher(pattern.substring(0, pattern.length() - 3));
            } else {
                matcher = new SpringAntMatcher(pattern);
            }
        }

        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
    }

    public boolean matches(HttpServletRequest request) {
        if (httpMethod != null && request.getMethod() != null && httpMethod != HttpMethod.valueOf(request.getMethod())) {
            return false;
        }
        if (MATCH_ALL.equals(pattern)) {
            return true;
        }
        String url = getRequestPath(request);
        logger.debug("request path: " + url);
        return matcher.matches(url);
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }
        return url;
    }

    private static interface Matcher {

        boolean matches(String path);
    }

    private static class SpringAntMatcher implements Matcher {

        private static final AntPathMatcher antMatcher = new AntPathMatcher();
        private final String                pattern;

        private SpringAntMatcher(String pattern) {
            this.pattern = pattern;
        }

        public boolean matches(String path) {
            return antMatcher.match(pattern, path);
        }
    }

    private static class SubpathMatcher implements Matcher {

        private final String subpath;
        private final int    length;

        private SubpathMatcher(String subpath) {
            this.subpath = subpath;
            this.length = subpath.length();
        }

        public boolean matches(String path) {
            return path.startsWith(subpath) && (path.length() == length || path.charAt(length) == '/');
        }
    }
}
