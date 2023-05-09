import com.kai.serialize.Serializer;
import com.kai.serialize.impl.JdkSerializer;
import dto.RpcRequest;

import java.util.UUID;

public class SerializerTester {
    public static void main(String[] args) {
        Serializer serializer = new JdkSerializer();
        RpcRequest rpcRequest = RpcRequest.builder().requestId(12).group("group")
                .version((byte) 1).interfaceName("test").build();
        byte[] b = serializer.encode(rpcRequest);

        RpcRequest decode = (RpcRequest) serializer.decode(b);
        System.out.println(decode);
    }
}
