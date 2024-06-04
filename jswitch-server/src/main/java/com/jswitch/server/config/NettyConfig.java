package com.jswitch.server.config;

import com.jswitch.server.listener.AsyncSipRequestListener;
import com.jswitch.server.listener.AsyncSipResponseListener;
import com.jswitch.server.server.SipTcpServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {

    @Value("${netty.sip.port}")
    private int sipPort;
    @Bean(initMethod = "start")
    public SipTcpServer sipTcpServer(AsyncSipRequestListener sipRequestListener, AsyncSipResponseListener sipResonseListener) {
        SipTcpServer sipTcpServer = new SipTcpServer(sipPort);
        sipTcpServer.addListener(sipRequestListener);
        sipTcpServer.addListener(sipRequestListener);
        return sipTcpServer;
    }

   /* @Bean(initMethod = "start")
    public SipUdpServer sipUdpServer() {
        return new SipUdpServer(sipPort);
    }*/
}
