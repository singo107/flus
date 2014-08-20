package cn.flus.account.core.exceptions;

/**
 * 密码不合法异常
 * 
 * @author zhouxing
 */
public class PasswordInvalidException extends IllegalArgumentException {

    private static final long serialVersionUID = 7355683176886321161L;

    public PasswordInvalidException() {
        super();
    }

    public PasswordInvalidException(String s) {
        super(s);
    }
}
