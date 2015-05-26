package cn.flus.cms.core.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4673487784260606284L;

    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String s) {
        super(s);
    }
}
