package com.kai.netty.server.codec;

import com.kai.serialize.Serializer;
import com.kai.serialize.SerializerFactory;
import com.kai.netty.server.RpcMessageConstants;
import dto.RpcMessage;
import exception.UnknownSerializerException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 将Rpc Message编码成二进制数据流，具体格式如下
 * ｜ magic number (4b)｜ message type (1b)｜ request id (4b)｜ version (1b)｜ codec (1b)｜ compress (1b)｜ length (4b)｜
 * ｜                                                    body                                                        ｜
 * @author wangkaiping
 * @date 2023.5.8
 */
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {

    public RpcMessageEncoder() {

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(RpcMessageConstants.MAGIC_NUMBER);
        byteBuf.writeByte(rpcMessage.getMessageType());
        byteBuf.writeInt(rpcMessage.getRequestId());
        byteBuf.writeByte(rpcMessage.getVersion());
        byteBuf.writeByte(rpcMessage.getCodec());
        byteBuf.writeByte(rpcMessage.getCompressType());
        Serializer serializer = SerializerFactory.createSerializer(rpcMessage.getCodec());
        if (serializer == null) {
            // TODO 自定义异常
            throw new UnknownSerializerException(rpcMessage.getCodec());
        }
        byte[] encode = serializer.encode(rpcMessage.getData());
        byteBuf.writeInt(encode.length);
        byteBuf.writeBytes(encode);
    }
}
