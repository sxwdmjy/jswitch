package com.jswitch.server.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SipMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readerIndex = in.readerIndex();
        int writerIndex = in.writerIndex();

        // 如果可读字节少于4个字节，说明尚不足以检查双CRLF，直接返回
        if (in.readableBytes() < 4) {
            return;
        }

        // 找到双CRLF的位置
        for (int i = readerIndex; i < writerIndex - 3; i++) {
            if (in.getByte(i) == '\r' && in.getByte(i + 1) == '\n'
                    && in.getByte(i + 2) == '\r' && in.getByte(i + 3) == '\n') {
                // 计算消息长度
                int length = i - readerIndex + 4;
                ByteBuf frame = in.readSlice(length).retain();
                String message = frame.toString(StandardCharsets.UTF_8);
                out.add(message);
                return;
            }
        }

        // 如果没有找到双CRLF，重置读取位置
        in.readerIndex(readerIndex);
    }
}
