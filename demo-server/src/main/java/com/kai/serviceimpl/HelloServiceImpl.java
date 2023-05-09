package com.kai.serviceimpl;

import com.kai.annotation.RpcService;

/**
 * @author wangkaiping
 */
@RpcService(version = "VERSION 1.0", group = "test1")
public class HelloServiceImpl {
    static {
        System.out.println("HelloServiceImpl被创建");
    }
}
