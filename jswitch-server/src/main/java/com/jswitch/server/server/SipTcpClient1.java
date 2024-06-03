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

public class SipTcpClient1 {


    public static void main(String[] args) throws InterruptedException {
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
        for (int i = 0; i < 2; i++) {
            channel.writeAndFlush("REGISTER sip:127.0.0.1:5060;transport=tcp SIP/2.0\r\n" +
                    "Via: SIP/2.0/TCP 127.0.0.1:57549;rport;branch=z9hG4bKPj3e4b844a63bd486db6fbf513fd16c468;alias\r\n" +
                    "Max-Forwards: 70\r\n" +
                    "From: \"1000\" <sip:1000@127.0.0.1>;tag=6ed6ce5d6aa54f1580a6e4ef59616396\r\n" +
                    "To: \"1000\" <sip:1000@127.0.0.1>\r\n" +
                    "Call-ID: 34436d2f2a0345af8d26dad12eb51063\r\n" +
                    "CSeq: 29605 REGISTER\r\n" +
                    "User-Agent: MicroSIP/3.21.3\r\n" +
                    "Supported: outbound, path\r\n" +
                    "Contact: \"1000\" <sip:1000@127.0.0.1:57549;transport=TCP;ob>;reg-id=1;+sip.instance=\"<urn:uuid:00000000-0000-0000-0000-0000e42d1d81>\"\r\n" +
                    "Expires: 300\r\n" +
                    "Allow: PRACK, INVITE, ACK, BYE, CANCEL, UPDATE, INFO, SUBSCRIBE, NOTIFY, REFER, MESSAGE, OPTIONS\r\n" +
                    "Content-Length:  0\r\n" +
                    "\r\n");
        }
    }
}
