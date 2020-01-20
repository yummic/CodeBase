package fun.clclcl.yummic.jsch.pool;

import com.jcraft.jsch.Session;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SshSessionPool extends GenericObjectPool<Session> {
    public SshSessionPool(PooledObjectFactory factory) {
        super(factory);
    }

    public SshSessionPool(PooledObjectFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    public SshSessionPool(PooledObjectFactory factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
