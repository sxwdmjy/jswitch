package com.jswitch.server.decoder;

import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
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
        int endOfHeaders = -1;
        for (int i = readerIndex; i < writerIndex - 3; i++) {
            if (in.getByte(i) == '\r' && in.getByte(i + 1) == '\n'
                    && in.getByte(i + 2) == '\r' && in.getByte(i + 3) == '\n') {
                endOfHeaders = i + 4;
                break;
            }
        }

        // 如果没有找到双CRLF，重置读取位置
        if (endOfHeaders == -1) {
            in.readerIndex(readerIndex);
            return;
        }

        // 解析头部
        ByteBuf headerBuf = in.slice(readerIndex, endOfHeaders - readerIndex).retain();
        String headers = headerBuf.toString(StandardCharsets.UTF_8);

        // 获取 Content-Length 头部
        int contentLength = 0;
        String[] lines = headers.split("\r\n");
        for (String line : lines) {
            if (line.toLowerCase().startsWith("content-length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
                break;
            }
        }

        // 如果整个消息体还没有完全接收完，则重置读取位置并返回
        if (in.readableBytes() < (endOfHeaders - readerIndex + contentLength)) {
            in.readerIndex(readerIndex);
            return;
        }

        // 读取完整消息
        int totalLength = endOfHeaders - readerIndex + contentLength;
        ByteBuf frame = in.readSlice(totalLength).retain();
        String message = frame.toString(StandardCharsets.UTF_8);
        if(log.isDebugEnabled()){
            log.info("decoder read msg：{}",message);
        }
        if(Objects.equals(message,"\r\n\r\n")){
            return;
        }
        if (message.startsWith("SIP/2.0")) {
            SipResponse response = new SipResponse(message);
            out.add(response);
        } else {
            SipRequest request = new SipRequest(message);
            // 解析请求消息
            out.add(request);
        }
    }
}
