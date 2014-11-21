package me.xuhongfeng.thriftpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 * @author xuhongfeng
 */
public class ThriftFactory<CLIENT extends TServiceClient> extends BasePooledObjectFactory<CLIENT> {

    private final TServiceClientFactory<CLIENT> clientFactory;
    private final TTransportFactory transportFactory;

    private TProtocolFactory protocolFactory;
    private ClientValidator<CLIENT> clientValidator;

    public ThriftFactory(ThriftPoolConfig config) {
        this.clientFactory = config.getClientFactory();
        this.transportFactory = config.getTransportFactory();
        this.protocolFactory = config.getProtocolFactory();
        this.clientValidator = config.getClientValidator();
    }

    @Override
    public CLIENT create() throws Exception {
        TTransport transport = transportFactory.getTransport(null);
        transport.open();
        TProtocol protocol = protocolFactory.getProtocol(transport);
        return clientFactory.getClient(protocol);
    }

    @Override
    public PooledObject<CLIENT> wrap(CLIENT client) {
        return new DefaultPooledObject<CLIENT>(client);
    }

    @Override
    public void destroyObject(PooledObject<CLIENT> p) throws Exception {
        p.getObject().getInputProtocol().getTransport().close();
        p.getObject().getOutputProtocol().getTransport().close();
    }

    @Override
    public boolean validateObject(PooledObject<CLIENT> p) {
        CLIENT client = p.getObject();
        if (!client.getInputProtocol().getTransport().isOpen()) {
            return false;
        }
        if (!client.getOutputProtocol().getTransport().isOpen()) {
            return false;
        }

        if (clientValidator != null) {
            try {
                return clientValidator.validate(client);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /********************************
     *
     *  Setter & Getter
     *
     ********************************/

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
