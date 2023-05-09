package com.kai.netty.server.codec;

import com.kai.serialize.Serializer;
import com.kai.serialize.SerializerFactory;
import com.kai.netty.server.RpcMessageConstants;
import dto.RpcMessage;
import exception.UnexpectedMessageTypeException;
import exception.UnexpectedVersionException;
import exception.UnknownSerializerException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * RpcMessage解码器，Netty接收信息时候会调用该解码器进行解码
 *
 * @author wangkaiping
 */
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;

    private static final int LENGTH_FIELD_OFFSET = 12;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public RpcMessageDecoder() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                -LENGTH_FIELD_OFFSET-LENGTH_FIELD_LENGTH, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 1. 利用LengthFieldBasedFrameDecoder的解码功能，避免粘包
        Object decode = super.decode(ctx, in);
        if (decode instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) decode;
            try {
                return decodeFrame(buf);
            }
            finally {
                buf.release();
            }
        }
        return decode;
    }

    private RpcMessage decodeFrame(ByteBuf in) {
        checkMagicNumber(in);
        byte type = in.readByte();
        if (type!=RpcMessageConstants.REQUEST_TYPE && type!=RpcMessageConstants.RESPONSE_TYPE && type!=RpcMessageConstants.HEARTBEAT_TYPE) {
            throw new UnexpectedMessageTypeException();
        }

        RpcMessage rpcMessage = new RpcMessage();
        int requestId = in.readInt();
        rpcMessage.setRequestId(requestId);
        byte version = in.readByte();
        if (version != RpcMessageConstants.VERSION) {
            throw new UnexpectedVersionException();
        }
        rpcMessage.setVersion(version);
        byte codec = in.readByte();
        if (!SerializerFactory.containsSerializer(codec)) {
            throw new UnknownSerializerException(codec);
        }
        Serializer serializer = SerializerFactory.createSerializer(codec);
        byte compress = in.readByte();
        // TODO 检验compress的类型

        int len = in.readInt();
        byte[] dataOfBytes = new byte[len];
        in.readBytes(dataOfBytes);
        Object data = serializer.decode(dataOfBytes);
        rpcMessage.setData(data);
        return rpcMessage;
    }

    private void checkMagicNumber(ByteBuf in) {
        int length = RpcMessageConstants.MAGIC_NUMBER.length;
        byte[] magicNumber = new byte[length];
        in.readBytes(magicNumber);
        for (int i=0; i<length; i++) {
            if (RpcMessageConstants.MAGIC_NUMBER[i] != magicNumber[i]) {
                throw new UnexpectedMessageTypeException();
            }
        }
    }
}
