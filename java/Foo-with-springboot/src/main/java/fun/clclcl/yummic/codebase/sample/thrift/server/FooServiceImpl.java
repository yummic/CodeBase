package fun.clclcl.yummic.codebase.sample.thrift.server;

import fun.clclcl.yummic.codebase.sample.thrift.FooService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class FooServiceImpl implements FooService.Iface {
    @Override
    public String getMessage(String info, int sleep) throws TException {
        try {
            Thread.sleep(sleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "get info : [" + info + "] after sleep : " + sleep;
    }

    public static void main(String[] args) {
        System.out.println("Thrift: try start server on port 8085...");
        TProcessor tProcessor = new FooService.Processor<FooService.Iface>(new FooServiceImpl());
        try {
            TServerSocket socket = new TServerSocket(8085, 10000);
            TServer.Args tArgs = new TServer.Args(socket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TSimpleServer(tArgs);
            server.serve();
            System.out.println("Thrift: server closed....");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
