package com.kai.serialize;

import dto.RpcRequest;
import dto.RpcResponse;

/**
 * 定义序列化工具接口
 *
 * @author wangkaiping
 */
public interface Serializer {

    /**
     * 序列化
     * @param rpcObject 待序列化的请求对象
     * @return 序列化结果
     */
    public byte[] encode(Object rpcObject);

    /**
     * 反序列化
     * @param bytes 反序列化的字符数组
     * @return 反序列化结果
     */
    public Object decode(byte[] bytes);
}
