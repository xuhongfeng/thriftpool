package me.xuhongfeng.thriftpool;

import org.apache.thrift.TServiceClient;

/**
 * @author xuhongfeng
 */
public abstract class ThriftCaller<CLIENT extends TServiceClient, RESULT> {

    public RESULT call(ThriftPool<CLIENT> pool) throws Exception {
        CLIENT client = pool.borrowObject();
        try {
            RESULT r = innerCall(client);
            pool.returnObject(client);
            return r;
        } catch (Throwable e) {
            pool.returnBrokenObject(client);
            throw new Exception(e);
        }
    }

    protected abstract RESULT innerCall(CLIENT client) throws Exception;
}
