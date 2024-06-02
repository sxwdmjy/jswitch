package com.jswitch.server.config;

import com.jswitch.server.SipTcpServer;
import com.jswitch.server.SipUdpServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class NettyConfig {

    @Value("${netty.sip.port}")
    private int sipPort;

    @Bean(initMethod = "start")
    public SipTcpServer sipTcpServer() {
        return new SipTcpServer(sipPort);
    }

   /* @Bean(initMethod = "start")
    public SipUdpServer sipUdpServer() {
        return new SipUdpServer(sipPort);
    }*/
}
