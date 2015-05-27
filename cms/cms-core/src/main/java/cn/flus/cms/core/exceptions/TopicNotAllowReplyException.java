package cn.flus.cms.core.exceptions;

public class TopicNotAllowReplyException extends RuntimeException {

    private static final long serialVersionUID = 8001810438067851374L;

    public TopicNotAllowReplyException() {
        super();
    }

    public TopicNotAllowReplyException(String s) {
        super(s);
    }
}
