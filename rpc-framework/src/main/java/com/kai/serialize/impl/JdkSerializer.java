package com.kai.serialize.impl;

import com.kai.serialize.Serializer;

import java.io.*;

/**
 * JDK的序列化器
 *
 * @author wangkaiping
 */
public class JdkSerializer implements Serializer {


    @Override
    public byte[] encode(Object rpcRequest) {
        byte[] result = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(rpcRequest);
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            // TODO 自定义异常类型
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Object decode(byte[] bytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
