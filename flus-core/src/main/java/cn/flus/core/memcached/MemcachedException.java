package cn.flus.core.memcached;

/**
 * Memcached异常
 * 
 * @author zhouxing 2015-5-10
 */
public class MemcachedException extends Exception {

    private static final long serialVersionUID = 4400894905538057194L;

    public MemcachedException() {
        super();
    }

    public MemcachedException(Throwable t) {
        super(t);
    }

    public MemcachedException(String error) {
        super(error);
    }

    public MemcachedException(String error, Throwable t) {
        super(error, t);
    }

}
