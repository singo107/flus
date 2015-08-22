package cn.flus.account.core.exceptions;

/**
 * 超过最大校验次数异常
 * 
 * @author singo
 */
public class ExceedMaxValidateException extends IllegalArgumentException {

    private static final long serialVersionUID = -6226049945474584624L;

    public ExceedMaxValidateException() {
        super();
    }

    public ExceedMaxValidateException(String s) {
        super(s);
    }
}
