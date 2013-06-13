package cn.flus.taobao.report.core.exception;

import org.apache.http.HttpStatus;

/**
 * root runtime exception
 * 
 * @author zhoux 2013-6-13
 */
public class RuntimeServerException extends RuntimeException implements HttpStatusCodeAware<RuntimeServerException>, ServerExceptionAware {

    private static final long serialVersionUID = 6244938842109763827L;

    protected int             httpStatus       = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public RuntimeServerException(String message) {
        super(message);
    }

    public RuntimeServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public RuntimeServerException setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
