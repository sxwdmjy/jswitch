package com.jswitch.server.config;

import com.jswitch.server.listener.AsyncSipRequestListener;
import com.jswitch.server.listener.AsyncSipResponseListener;
import com.jswitch.server.server.SipTcpServer;
import com.jswitch.server.server.SipUdpServer;
import com.jswitch.sip.message.MessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class NettyConfig {

    @Value("${netty.sip.port}")
    private int sipPort;
    @Bean(initMethod = "start")
    public SipTcpServer sipTcpServer(MessageFactory messageFactory, AsyncSipRequestListener sipRequestListener, AsyncSipResponseListener sipResonseListener) {
        SipTcpServer sipTcpServer = new SipTcpServer(sipPort);
        sipTcpServer.addListener(sipRequestListener);
        sipTcpServer.addListener(sipResonseListener);
        sipTcpServer.setMessageFactory(messageFactory);
        return sipTcpServer;
    }

    @Bean(initMethod = "start")
    public SipUdpServer sipUdpServer(MessageFactory messageFactory,AsyncSipRequestListener sipRequestListener, AsyncSipResponseListener sipResonseListener) {
        SipUdpServer sipUdpServer = new SipUdpServer(sipPort);
        sipUdpServer.setMessageFactory(messageFactory);
        sipUdpServer.addListener(sipRequestListener);
        sipUdpServer.addListener(sipResonseListener);
        return sipUdpServer;
    }
}
