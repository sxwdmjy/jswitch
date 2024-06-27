package com.jswitch.server.handler;

import com.jswitch.server.listener.AsyncSipRequestListener;
import com.jswitch.server.listener.AsyncSipResponseListener;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.msg.SipMessageListener;
import com.jswitch.sip.SipMessage;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SipUdpRequestHandler extends SimpleChannelInboundHandler<SipMessage> {

    private List<SipMessageListener> listenerList;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SipMessage message) throws Exception {
        //String remoteAddress = ctx.channel().remoteAddress().toString();
        // message.setChannelId(ctx.channel().id().asLongText());
        // message.setRemoteIp(remoteAddress.substring(1, remoteAddress.lastIndexOf(':')));
        //  message.setRemotePort(remoteAddress.substring(remoteAddress.lastIndexOf(':') + 1));
        SipMessageEvent event = new SipMessageEvent(message, ctx);
        if (message instanceof SipRequest) {
            SipMessageListener sipMessageListener = listenerList.stream().filter(listener -> listener instanceof AsyncSipRequestListener).findFirst().orElse(null);
            if (sipMessageListener != null) {
                sipMessageListener.onMessage(event);
            }
        } else if (message instanceof SipResponse) {
            SipMessageListener sipMessageListener = listenerList.stream().filter(listener -> listener instanceof AsyncSipResponseListener).findFirst().orElse(null);
            if (sipMessageListener != null) {
                sipMessageListener.onMessage(event);
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    public void setListeners(List<SipMessageListener> listenerList) {
        this.listenerList = listenerList;
    }
}
