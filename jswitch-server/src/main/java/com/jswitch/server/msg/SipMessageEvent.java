package com.jswitch.server.msg;

import com.jswitch.sip.SipMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

@Getter
public class SipMessageEvent {

    private final SipMessage message;

    private final ChannelHandlerContext ctx;

    public SipMessageEvent(SipMessage msg, ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.message = msg;
    }

}
