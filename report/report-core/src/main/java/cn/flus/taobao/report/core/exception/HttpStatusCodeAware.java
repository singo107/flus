package cn.flus.taobao.report.core.exception;

/**
 * 是否需要设置HTTP报头返回码
 * 
 * @author zhoux 2013-6-13
 */
public interface HttpStatusCodeAware<T> {

    public int getHttpStatus();

    public T setHttpStatus(int httpStatus);

}
