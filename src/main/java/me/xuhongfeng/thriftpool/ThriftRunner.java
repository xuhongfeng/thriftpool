package me.xuhongfeng.thriftpool;

import org.apache.thrift.TServiceClient;

/**
 * @author xuhongfeng
 */
public abstract class ThriftRunner<CLIENT extends TServiceClient> {

    public void run(ThriftPool<CLIENT> pool) throws Exception {
        CLIENT client = pool.borrowObject();
        try {
            innerRun(client);
            pool.returnObject(client);
        } catch (Throwable e) {
            pool.returnBrokenObject(client);
            throw new Exception(e);
        }
    }

    protected abstract void innerRun(CLIENT client) throws Exception;
}
