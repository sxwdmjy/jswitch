package com.jswitch.server.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

@Getter
public class SipMessageEvent {

    private final String message;

    private final ChannelHandlerContext ctx;

    public SipMessageEvent(String msg, ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.message = msg;
    }

}
