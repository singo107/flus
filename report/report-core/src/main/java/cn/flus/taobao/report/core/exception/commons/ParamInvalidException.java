package cn.flus.taobao.report.core.exception.commons;

import cn.flus.taobao.report.core.exception.RuntimeServerException;

/**
 * 参数不合法
 * 
 * @author zhoux 2013-5-15
 */
public class ParamInvalidException extends RuntimeServerException {

    private static final long serialVersionUID = 6317207439437317916L;

    public ParamInvalidException(String msg) {
        super(msg);
    }
}
