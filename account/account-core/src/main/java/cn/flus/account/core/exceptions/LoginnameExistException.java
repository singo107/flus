package cn.flus.account.core.exceptions;

/**
 * 用户名已经占用异常
 * 
 * @author singo
 */
public class LoginnameExistException extends RuntimeException {

    private static final long serialVersionUID = 7924444544807688449L;

    public LoginnameExistException() {
        super();
    }

    public LoginnameExistException(String s) {
        super(s);
    }
}
