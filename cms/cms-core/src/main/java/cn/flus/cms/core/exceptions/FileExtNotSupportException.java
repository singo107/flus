package cn.flus.cms.core.exceptions;

public class FileExtNotSupportException extends RuntimeException {

    private static final long serialVersionUID = -5004307019156593071L;

    public FileExtNotSupportException() {
        super();
    }

    public FileExtNotSupportException(String s) {
        super(s);
    }
}
