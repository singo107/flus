package cn.flus.core.memcached;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.SockIOPool;

/**
 * Memcached连接池
 * 
 * @author zhouxing 2015-5-10
 */
public class MemcachedPool {

    private static final Logger  logger             = LoggerFactory.getLogger(MemcachedPool.class);
    private static Properties    POOL_DEFAULT_VALUE = new PoolConfig();

    private static MemcachedPool instance           = new MemcachedPool();

    private MemcachedPool() {
    }

    public static MemcachedPool getInstance() {
        return instance;
    }

    public void initPool(List<MemcachedServer> servers) throws MemcachedException {
        initPool(servers, POOL_DEFAULT_VALUE);
    }

    public void initPool(List<MemcachedServer> servers, Properties props) throws MemcachedException {
        SockIOPool sockIOPool = SockIOPool.getInstance();

        // server & weight
        sockIOPool.setServers(getServer(servers));
        sockIOPool.setWeights(getWeight(servers));

        // bean props
        Set keys = props.keySet();
        Iterator keyIter = keys.iterator();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            String value = props.getProperty(key);
            if (value == null) {
                value = POOL_DEFAULT_VALUE.getProperty(key);
            }
            try {
                Class type = PropertyUtils.getPropertyType(sockIOPool, key);
                logger.debug("Type=" + type + ";Key=" + key + ";Value=" + value);
                Object val = ConvertUtils.convert(value, type);
                PropertyUtils.setSimpleProperty(sockIOPool, key, val);
            } catch (IllegalAccessException e) {
                throw new MemcachedException("Init Pool Fail", e);
            } catch (InvocationTargetException e) {
                throw new MemcachedException("Init Pool Fail", e);
            } catch (NoSuchMethodException e) {
                throw new MemcachedException("Init Pool Fail", e);
            }
        }
        sockIOPool.initialize();
    }

    private Integer[] getWeight(List<MemcachedServer> weigths) {
        Integer[] w = new Integer[weigths.size()];
        for (int i = 0; i < weigths.size(); i++) {
            w[i] = weigths.get(i).getWeight();
        }
        return w;
    }

    private String[] getServer(List<MemcachedServer> servers) {
        String[] s = new String[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            MemcachedServer server = servers.get(i);
            s[i] = server.getAddress() + ":" + server.getPort();
        }
        return s;
    }
}
