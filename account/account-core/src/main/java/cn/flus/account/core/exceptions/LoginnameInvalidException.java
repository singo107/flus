package cn.flus.account.core.exceptions;

/**
 * 用户名不合法异常
 * 
 * @author singo
 */
public class LoginnameInvalidException extends IllegalArgumentException {

    private static final long serialVersionUID = -161154300311902776L;

    public LoginnameInvalidException() {
        super();
    }

    public LoginnameInvalidException(String s) {
        super(s);
    }
}
