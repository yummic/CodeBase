package fun.clclcl.yummic.jsch.pool;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import fun.clclcl.yummic.jsch.SshLogger;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SshSessionManager {

    private final long MINUTE = 60 * 1000L;

    private final long DEFAULT_MAX_WAIT_MILLIS = 1 * MINUTE;

    private final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 10 * MINUTE;

    private final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 5 * MINUTE;

    private Map<String, SshSessionPool> poolMap = new HashMap<>();

    private GenericObjectPoolConfig poolConfig;

    private AbandonedConfig abandonedConfig;

    private SshLogger logger = SshLogger.DEFAULT;

    private final Lock lock;

    public SshSessionManager() {
        this.lock = new ReentrantLock();
        initDefaultPoolConfig();
    }

    private void initDefaultPoolConfig() {
        this.poolConfig = new GenericObjectPoolConfig();
        //this.poolConfig.setMaxTotal(5);
        //this.poolConfig.setMaxIdle(5);
        this.poolConfig.setTestOnBorrow(true);
        this.poolConfig.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
        this.poolConfig.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        this.poolConfig.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
    }

    public SshSessionManager(GenericObjectPoolConfig poolConfig, AbandonedConfig abandonedConfig) {
        this.lock = new ReentrantLock();
        if (poolConfig == null) {
            initDefaultPoolConfig();
        } else {
            this.poolConfig = poolConfig.clone();
        }
        if (abandonedConfig == null) {
            this.abandonedConfig = null;
        } else {
            this.abandonedConfig = new AbandonedConfig();
            this.abandonedConfig.setLogAbandoned(abandonedConfig.getLogAbandoned());
            this.abandonedConfig.setLogWriter(abandonedConfig.getLogWriter());
            this.abandonedConfig.setRemoveAbandonedOnBorrow(abandonedConfig.getRemoveAbandonedOnBorrow());
            this.abandonedConfig.setRemoveAbandonedOnMaintenance(abandonedConfig.getRemoveAbandonedOnMaintenance());
            this.abandonedConfig.setRemoveAbandonedTimeout(abandonedConfig.getRemoveAbandonedTimeout());
            this.abandonedConfig.setUseUsageTracking(abandonedConfig.getUseUsageTracking());
            this.abandonedConfig.setRequireFullStackTrace(abandonedConfig.getRequireFullStackTrace());
        }
    }

    public void remove(String host) {
        if (host == null)
            return;
        SshSessionPool pool;
        this.lock.lock();
        try {
            pool = poolMap.remove(host);
        } finally {
            this.lock.unlock();
        }
        if (pool != null) {
            pool.close();
        }
    }

    private SshSessionPool ensurePoolInitialized(SshConfig hostConfig) throws JSchException {
        SshSessionPool pool;
        this.lock.lock();
        try {
            pool = poolMap.get(hostConfig.getHost());
            if (pool == null) {
                SessionFactory factory = new SessionFactory(hostConfig);
                factory.setExceptionConsumer(logger);
                pool = new SshSessionPool(factory, poolConfig);
                poolMap.put(hostConfig.getHost(), pool);
            }
        } finally {
            this.lock.unlock();
        }
        return pool;
    }

    public Session getSession(SshConfig hostConfig) throws Exception {
        Objects.requireNonNull(hostConfig);
        Objects.requireNonNull(hostConfig.getHost());
        Objects.requireNonNull(hostConfig.getUser());

        SshSessionPool pool = ensurePoolInitialized(hostConfig);
        Session session = pool.borrowObject();
        return session;
    }

    public void returnSession(String host, Session session) {
        if (host == null || session == null) {
            return;
        }
        SshSessionPool pool = poolMap.get(host);
        if (pool != null) {
            pool.returnObject(session);
        }
    }

    public void setExceptionConsumer(SshLogger logger) {
        this.logger = logger;
    }

    public void shutdown() {
        this.lock.lock();
        try {
            poolMap.forEach((host, pool) -> {
                pool.close();
                logger.log(Logger.INFO, "Close ssh connection pool " + host);
            });
        } finally {
            this.lock.unlock();
        }
    }

}
