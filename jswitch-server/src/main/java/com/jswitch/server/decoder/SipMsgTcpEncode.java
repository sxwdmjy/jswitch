package com.jswitch.server.decoder;

import com.jswitch.sip.SipResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class SipMsgTcpEncode extends MessageToMessageEncoder<SipResponse> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SipResponse msg, List out) throws Exception {
        if (log.isDebugEnabled()) {
            log.info("sipResponse:\n{}", msg.encode());
        }
        out.add(Unpooled.copiedBuffer(msg.encode(), CharsetUtil.UTF_8));
    }
}
