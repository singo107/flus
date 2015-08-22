package cn.flus.account.web.bean;

import java.io.Serializable;

/**
 * 标准的JSON返回
 * 
 * @author singo
 */
public class StandardResponse<T> implements Serializable {

    private static final long   serialVersionUID = 6385535435249046342L;

    private String              code;
    private T                   t;
    private String              message;

    private final static String SUCCESS          = "success";

    public StandardResponse() {
    }

    public StandardResponse(T t) {
        this.code = SUCCESS;
        this.t = t;
    }

    public StandardResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StandardResponse [code=" + code + ", t=" + t + ", message=" + message + "]";
    }
}
