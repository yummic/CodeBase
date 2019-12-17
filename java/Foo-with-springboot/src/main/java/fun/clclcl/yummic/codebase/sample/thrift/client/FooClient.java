package fun.clclcl.yummic.codebase.sample.thrift.client;

import fun.clclcl.yummic.codebase.sample.thrift.FooService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class FooClient {
    public static void main(String[] args) {

        try {
            TSocket transport = new TSocket("127.0.0.1", 8085);
            transport.setTimeout(2100);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);

            FooService.Client client = new FooService.Client(protocol);
            transport.open();
            String result = client.getMessage("okkkkkk", 1);
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
