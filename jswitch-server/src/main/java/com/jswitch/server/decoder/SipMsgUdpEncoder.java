package com.jswitch.server.decoder;

import com.jswitch.sip.SipResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SocketUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.List;

/**
 * @author danmo
 * @date 2024-06-26 10:42
 **/
@Slf4j
public class SipMsgUdpEncoder extends MessageToMessageEncoder<SipResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SipResponse sipResponse, List out) throws Exception {
        if(log.isDebugEnabled()){
            log.info("sipResponse:\n{}", sipResponse.encode());
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(sipResponse.encode(), CharsetUtil.UTF_8);
        InetAddress remoteAddress = sipResponse.getRemoteAddress();
        int remotePort = sipResponse.getRemotePort();
        out.add(new DatagramPacket(byteBuf, SocketUtils.socketAddress(remoteAddress.getHostAddress(), remotePort)));
    }
}
