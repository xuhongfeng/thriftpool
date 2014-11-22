package me.xuhongfeng.thriftpool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransportFactory;

/**
 * @author xuhongfeng
 */
public class ThriftPoolConfig<CLIENT extends TServiceClient> extends GenericObjectPoolConfig {

    private final TServiceClientFactory<CLIENT> clientFactory;

    private final TTransportFactory transportFactory;

    private TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

    private ClientValidator<CLIENT> clientValidator;

    public ThriftPoolConfig(TServiceClientFactory<CLIENT> clientFactory, TTransportFactory transportFactory) {
        this.clientFactory = clientFactory;
        this.transportFactory = transportFactory;

        setMaxTotal(200);
        setMaxIdle(50);
        setMinIdle(5);

        setMaxWaitMillis(30000L);

        setTestOnCreate(true);

        setTimeBetweenEvictionRunsMillis(60000L);
        setNumTestsPerEvictionRun(-10);
        setTestWhileIdle(true);
    }

    public TServiceClientFactory<CLIENT> getClientFactory() {
        return clientFactory;
    }

    public TTransportFactory getTransportFactory() {
        return transportFactory;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public ClientValidator<CLIENT> getClientValidator() {
        return clientValidator;
    }

    public void setClientValidator(ClientValidator<CLIENT> clientValidator) {
        this.clientValidator = clientValidator;
    }
}
