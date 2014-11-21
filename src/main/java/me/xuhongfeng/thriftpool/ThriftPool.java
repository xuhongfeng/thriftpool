package me.xuhongfeng.thriftpool;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;

/**
 * @author xuhongfeng
 */
public class ThriftPool<CLIENT extends TServiceClient> extends GenericObjectPool<CLIENT> {

    public ThriftPool(ThriftPoolConfig<CLIENT> config) {
        super(new ThriftFactory<CLIENT>(config), config);

        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnMaintenance(true);
        setAbandonedConfig(abandonedConfig);
    }

    public void returnBrokenObject(CLIENT client) throws Exception {
        invalidateObject(client);
    }
}
