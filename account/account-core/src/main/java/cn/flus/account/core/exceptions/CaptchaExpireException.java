package cn.flus.account.core.exceptions;

/**
 * 验证码过期
 * 
 * @author singo
 */
public class CaptchaExpireException extends IllegalArgumentException {

    private static final long serialVersionUID = 2175684686762896393L;

    public CaptchaExpireException() {
        super();
    }

    public CaptchaExpireException(String s) {
        super(s);
    }
}
