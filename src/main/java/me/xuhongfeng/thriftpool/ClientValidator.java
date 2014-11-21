package me.xuhongfeng.thriftpool;

import org.apache.thrift.TServiceClient;

/**
 * @author xuhongfeng
 */
public interface ClientValidator<CLIENT extends TServiceClient> {
    public boolean validate(CLIENT client) throws Exception;
}
