package cn.flus.cms.core.exceptions;

public class FileLengthExceedException extends RuntimeException {

    private static final long serialVersionUID = -5004307019156593071L;

    public FileLengthExceedException() {
        super();
    }

    public FileLengthExceedException(String s) {
        super(s);
    }
}
