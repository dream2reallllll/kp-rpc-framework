package com.kai;

import com.kai.annotation.RpcScan;
import com.kai.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 服务器启动器
 *
 * @author wangkaiping
 */
@Slf4j
@RpcScan(basePackage = {"com.kai"})
public class Main {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        System.out.println(nettyServer.getServiceProvider());
        log.info("start the server.");
        nettyServer.start();
    }
}
