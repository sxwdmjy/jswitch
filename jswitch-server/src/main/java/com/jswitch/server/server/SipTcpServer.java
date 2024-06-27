package com.jswitch.server.server;

import com.jswitch.server.decoder.SipMsgTcpDecoder;
import com.jswitch.server.decoder.SipMsgTcpEncode;
import com.jswitch.server.handler.SipTcpRequestHandler;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.message.MessageFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SipTcpServer {


    private Integer port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ChannelFuture channelFuture;

    private List<SipMessageListener> listenerList;

    private MessageFactory messageFactory;


    public SipTcpServer(int port) {
        this.port = port;
        this.listenerList = new ArrayList<>();
    }

    private void start() {
       new Thread(() -> {
           try {
               bossGroup = new NioEventLoopGroup(1);
               workerGroup = new NioEventLoopGroup();
               ServerBootstrap b = new ServerBootstrap();
               b.group(bossGroup, workerGroup)
                       .channel(NioServerSocketChannel.class)
                       .handler(new LoggingHandler(LogLevel.INFO))
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                           @Override
                           public void initChannel(SocketChannel ch) throws Exception {
                               ch.pipeline().addLast(new SipMsgTcpDecoder(messageFactory));
                               ch.pipeline().addLast(new SipMsgTcpEncode());
                               //ch.pipeline().addLast(new StringEncoder());
                               SipTcpRequestHandler sipTcpRequestHandler = new SipTcpRequestHandler();
                               sipTcpRequestHandler.setListeners(listenerList);
                               ch.pipeline().addLast(sipTcpRequestHandler);
                           }
                       })
                       .option(ChannelOption.SO_BACKLOG, 128)
                       .childOption(ChannelOption.SO_KEEPALIVE, true);

               channelFuture = b.bind(port).sync();
               log.info("TCP 监听服务启动");
               channelFuture.channel().closeFuture().sync();
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           } finally {
               workerGroup.shutdownGracefully();
               bossGroup.shutdownGracefully();
           }
        }).start();

    }

    @PreDestroy
    public void stop() {
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }

    }

    public void addListener(SipMessageListener listener) {
        listenerList.add(listener);
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }
}
