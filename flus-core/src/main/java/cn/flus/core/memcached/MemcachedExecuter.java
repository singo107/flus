package cn.flus.core.memcached;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.danga.MemCached.MemCachedClient;

public class MemcachedExecuter {

    private static MemcachedExecuter instance = new MemcachedExecuter();

    private MemcachedExecuter() {
        init();
    }

    public static MemcachedExecuter getInstance() {
        return instance;
    }

    private MemCachedClient client = new MemCachedClient();

    private void init() {
        client.setPrimitiveAsString(true);
    }

    public boolean set(String key, Object value) {
        return client.set(key, value);
    }

    public boolean set(String key, Object value, Date expired) {
        return client.set(key, value, expired);
    }

    public Object get(String key) {
        return client.get(key);
    }

    public void printStat() {
        Map stats = client.stats();
        Set keys = stats.keySet();
        Iterator keyIter = keys.iterator();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            Object value = stats.get(key);
            System.out.println(key + "=" + value);
        }
    }

    public static void main(String[] args) {
        try {
            MemcachedServer server = new MemcachedServer("127.0.0.1", 11211, 1);
            List<MemcachedServer> servers = new ArrayList<MemcachedServer>();
            servers.add(server);
            MemcachedPool pool = MemcachedPool.getInstance();
            pool.initPool(servers);
            MemcachedExecuter client = MemcachedExecuter.getInstance();
            String value = (String) client.get("test1");
            System.out.println("value=" + value);
            client.set("test1", "value1");
            value = (String) client.get("test1");
            System.out.println("value=" + value);
            client.printStat();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }
}
