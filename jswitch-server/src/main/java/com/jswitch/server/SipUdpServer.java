package com.jswitch.server;

import com.jswitch.server.handler.SipUdpRequestHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PreDestroy;

public class SipUdpServer {

    private final int port;

    private NioEventLoopGroup group;

    private ChannelFuture channelFuture;

    public SipUdpServer(int port) {
        this.port = port;
    }


    private void start() {
        try {
            group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<DatagramChannel>() {

                        @Override
                        protected void initChannel(DatagramChannel channel) throws Exception {
                            channel.pipeline().addLast(new SipUdpRequestHandler());
                        }
                    });

            channelFuture = b.bind(port).sync();
            channelFuture.channel().closeFuture().await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stop() {
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}
