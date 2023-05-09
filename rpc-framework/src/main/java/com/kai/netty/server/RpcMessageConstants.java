package com.kai.netty.server;

/**
 * 常量类，用于存放相关的常量值
 *
 * @author wangkaiping
 */
public final class RpcMessageConstants {

    private RpcMessageConstants(){

    }

    public static final byte[] MAGIC_NUMBER = {(byte) 'w', (byte) 'r', (byte) 'p', (byte) 'c'};

    public static final byte VERSION = 1;

    /**
     * 请求类型
     */
    public static final byte REQUEST_TYPE = 0;

    public static final byte RESPONSE_TYPE = 1;

    public static final byte HEARTBEAT_TYPE = 2;

}
