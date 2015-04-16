package cn.flus.account.core.exceptions;

/**
 * 用户名不存在异常
 * 
 * @author singo
 */
public class LoginnameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7871209620190396118L;

    public LoginnameNotFoundException() {
        super();
    }

    public LoginnameNotFoundException(String s) {
        super(s);
    }
}
