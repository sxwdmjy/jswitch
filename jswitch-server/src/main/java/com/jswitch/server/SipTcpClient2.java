package com.jswitch.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SipTcpClient2 {


    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        //设置线程组
        bootstrap.group(group)
                .channel(NioSocketChannel.class) //设置客户端的通道实现类型
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new LineBasedFrameDecoder(2048));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                System.out.println(o);
                            }
                        }); //加入自己的处理器
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 5060).channel();
        for (int i = 0; i < 100_000; i++) {
            System.out.println("输入：");
            channel.writeAndFlush("client2-"+ i + "\r\n");
        }
    }
}
