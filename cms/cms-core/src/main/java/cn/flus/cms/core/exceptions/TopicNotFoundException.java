package cn.flus.cms.core.exceptions;

public class TopicNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4673487784260606284L;

    public TopicNotFoundException() {
        super();
    }

    public TopicNotFoundException(String s) {
        super(s);
    }
}
