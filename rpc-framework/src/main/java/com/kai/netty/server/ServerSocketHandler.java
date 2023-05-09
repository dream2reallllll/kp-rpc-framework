package com.kai.netty.server;

import com.kai.provider.ServiceProvider;
import com.kai.serialize.SerializerFactory;
import dto.RpcMessage;
import dto.RpcRequest;
import dto.RpcResponse;
import exception.UnknownServiceException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;


/**
 * 服务器handler，定义服务流程
 *
 * @author wangkaiping
 */
@Setter
public class ServerSocketHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ServiceProvider serviceProvider;

    public ServerSocketHandler() {

    }



    /**
     * 预想流程
     * 1、判断消息类型 （请求or心跳）
     * 2、如果是请求
     *      3、将RpcMessage的data转换为RpcRequest
     *      4、找到Request对应的服务名称以及参数等
     *      5、查找该类，并调用对应的方法
     *      6、封装响应对象
     *      7、返回响应
     * 2、如果是心跳
     *      3、封装心跳响应
     *      4、返回响应
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        RpcMessage rpcMessage = (RpcMessage) o;
        RpcMessage responseMessage = new RpcMessage();
        responseMessage.setVersion(RpcMessageConstants.VERSION);
        responseMessage.setRequestId(rpcMessage.getRequestId());
        responseMessage.setCodec(SerializerFactory.JDK_SERIALIZER);
        // TODO 设置压缩格式
        // responseMessage.setCompressType();

        if (rpcMessage.getMessageType() == RpcMessageConstants.REQUEST_TYPE) {
            RpcResponse rpcResponse = new RpcResponse();
            Object data = rpcMessage.getData();
            if (data instanceof RpcRequest) {
                RpcRequest rpcRequest = (RpcRequest) data;
                Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
                if (service == null) {
                    throw new UnknownServiceException();
                }
                Method method = service.getClass().getMethod(rpcRequest.getInterfaceName());
                Object result = method.invoke(service, rpcRequest.getParameters());
                rpcResponse.succeed(result);
                responseMessage.setMessageType(RpcMessageConstants.RESPONSE_TYPE);
                responseMessage.setData(rpcResponse);
            }
            else {
                rpcResponse.failed("Unexpected request type.");
            }
        }
        else if (rpcMessage.getMessageType() == RpcMessageConstants.HEARTBEAT_TYPE) {
            // TODO 支持心跳信息
        }
    }
}
