package me.xuhongfeng.thriftpool.sample;

import me.xuhongfeng.thriftpool.*;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 * @author xuhongfeng
 */
public class Sample {

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 8080;

        SampleThrift.Client.Factory clientFactory = new SampleThrift.Client.Factory();

        TTransportFactory transportFactory = new TTransportFactory() {
            @Override
            public TTransport getTransport(TTransport trans) {
                return new TSocket(host, port);
            }
        };

        ClientValidator<SampleThrift.Client> clientValidator = new ClientValidator<SampleThrift.Client>() {
            @Override
            public boolean validate(SampleThrift.Client client) throws Exception {
                return client.ping().equals("PONG");
            }
        };

        ThriftPoolConfig<SampleThrift.Client> config =
                new ThriftPoolConfig<SampleThrift.Client>(clientFactory, transportFactory);

        ThriftPool<SampleThrift.Client> pool = new ThriftPool<SampleThrift.Client>(config);

        final int userId = 1;
        final User user = new ThriftCaller<SampleThrift.Client, User>() {
            @Override
            protected User innerCall(SampleThrift.Client client) throws Exception {
                try {
                    return client.get(userId);
                } catch (UserNotFoundException e) {
                    return null;
                }
            }
        }.call(pool);

        new ThriftRunner<SampleThrift.Client>() {
            @Override
            protected void innerRun(SampleThrift.Client client) throws Exception {
                client.save(user);
            }
        }.run(pool);
    }
}
