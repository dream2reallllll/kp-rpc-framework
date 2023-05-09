package com.kai.netty.server;

import com.kai.provider.ServiceProvider;
import com.kai.netty.server.codec.RpcMessageDecoder;
import com.kai.netty.server.codec.RpcMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The server implemented by netty framework.
 *
 * @author wangkaiping
 * @date 2023.4.28
 */
@Component
@Setter
@Getter
public class NettyServer {

    private static final int PORT = 8899;

    @Autowired
    private ServiceProvider serviceProvider;

    public NettyServer() {

    }

    public void start() throws InterruptedException {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup eventGroup = new NioEventLoopGroup(cpuCount * 2);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, eventGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                nioSocketChannel.pipeline().addLast(new RpcMessageEncoder());
                                nioSocketChannel.pipeline().addLast(new RpcMessageDecoder());
                                nioSocketChannel.pipeline().addLast(new ServerSocketHandler());
                            }
                        }
                )
                .bind(PORT)
                .sync();
    }
}
