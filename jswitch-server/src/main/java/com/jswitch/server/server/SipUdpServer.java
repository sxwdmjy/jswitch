package com.jswitch.server.server;

import com.jswitch.server.decoder.SipMsgTcpDecoder;
import com.jswitch.server.decoder.SipMsgUdpDecoder;
import com.jswitch.server.decoder.SipMsgUdpEncoder;
import com.jswitch.server.handler.SipUdpRequestHandler;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.message.MessageFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SipUdpServer {

    private final int port;

    private NioEventLoopGroup group;

    private ChannelFuture channelFuture;

    private MessageFactory messageFactory;

    private List<SipMessageListener> listenerList;

    public SipUdpServer(int port) {
        this.port = port;
        listenerList = new ArrayList<>(10);
    }


    private void start() {
        new Thread(() -> {
            try {
                group = new NioEventLoopGroup();
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioDatagramChannel.class)
                        .option(ChannelOption.SO_BROADCAST, true)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .handler(new ChannelInitializer<DatagramChannel>() {

                            @Override
                            protected void initChannel(DatagramChannel channel) throws Exception {
                                channel.pipeline().addLast(new SipMsgUdpDecoder(messageFactory));
                                channel.pipeline().addLast(new SipMsgUdpEncoder());
                                SipUdpRequestHandler sipUdpRequestHandler = new SipUdpRequestHandler();
                                sipUdpRequestHandler.setListeners(listenerList);
                                channel.pipeline().addLast(sipUdpRequestHandler);
                            }
                        });

                channelFuture = b.bind(port).sync();
                log.info("UDP 监听服务启动");
                channelFuture.channel().closeFuture().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                group.shutdownGracefully();
            }
        }).start();
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

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void addListener(SipMessageListener listener) {
        listenerList.add(listener);
    }
}
