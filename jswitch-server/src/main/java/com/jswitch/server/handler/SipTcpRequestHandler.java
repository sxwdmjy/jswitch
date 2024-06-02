package com.jswitch.server.handler;

import com.jswitch.server.listener.AsyncSipMessageListener;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.utils.SipChannelManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
public class SipTcpRequestHandler extends SimpleChannelInboundHandler<String> {

    private final AsyncSipMessageListener sipMessageListener;

    public SipTcpRequestHandler(AsyncSipMessageListener sipMessageListener) {
        this.sipMessageListener = sipMessageListener;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        if(!StringUtils.hasLength(msg) && msg.split("\r\n").length == 0){
            return;
        }
        //log.info("tcp channelRead0: channelId-{} \n{}",channelId, msg);
        SipMessageEvent event = new SipMessageEvent(msg, ctx);
        // 监听器
        sipMessageListener.onMessage(event);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        log.info("tcp channelActive: channelId-{}", channelId);
        SipChannelManager.add(channelId, ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        log.info("tcp channelInactive: channelId-{}", channelId);
        SipChannelManager.remove(channelId);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        SipChannelManager.remove(channelId);
    }

}
