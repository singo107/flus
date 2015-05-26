package cn.flus.cms.core.exceptions;

public class FileUploadFailedException extends RuntimeException {

    private static final long serialVersionUID = -5004307019156593071L;

    public FileUploadFailedException() {
        super();
    }

    public FileUploadFailedException(String s) {
        super(s);
    }
}
