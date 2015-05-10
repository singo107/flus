package cn.flus.core.memcached;

import java.util.Properties;

public class PoolConfig extends Properties {

    private static final long serialVersionUID = 1460023220021716113L;

    public PoolConfig() {
        super();
        initDefault();
    }

    private void initDefault() {
        initConn();
        initMainSleep();
        initTCP();
        initFailover();
        initAliveCheck();
    }

    protected void initConn() {
        setProperty("initConn", "10");
        setProperty("minConn", "10");
        setProperty("maxConn", "20");
        setProperty("maxIdle", String.valueOf(1000 * 60 * 30));
    }

    protected void initMainSleep() {
        setProperty("maintSleep", String.valueOf(1000 * 5));
    }

    protected void initTCP() {
        setProperty("nagle", "false");
        setProperty("socketTO", String.valueOf(1000 * 3));
        setProperty("socketConnectTO", String.valueOf(1000 * 3));
    }

    protected void initFailover() {
        setProperty("failover", "true");
        setProperty("failback", "true");
    }

    protected void initAliveCheck() {
        setProperty("aliveCheck", "true");
    }
}
