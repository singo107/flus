package cn.flus.taobao.report.core.exception;

import org.apache.http.HttpStatus;

/**
 * root exception
 * 
 * @author zhoux 2013-6-13
 */
public class ServerException extends Exception implements HttpStatusCodeAware<ServerException>, ServerExceptionAware {

    private static final long serialVersionUID = 2257789620991047103L;

    protected int             httpStatus       = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public ServerException setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
