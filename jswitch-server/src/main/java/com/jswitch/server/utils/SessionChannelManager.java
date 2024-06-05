package com.jswitch.server.utils;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionChannelManager {

    public static Map<String, ChannelHandlerContext> channelHandlerContextMap = new ConcurrentHashMap<>();

    public static void add(String key, ChannelHandlerContext channelHandlerContext) {
        if(channelHandlerContextMap.containsKey(key)){
            return;
        }
        channelHandlerContextMap.put(key, channelHandlerContext);
    }

    public static void remove(String key) {
        channelHandlerContextMap.remove(key);
    }

    public static void removeAndClose(String key) {
        if (channelHandlerContextMap.containsKey(key)) {
            ChannelHandlerContext channelHandlerContext = channelHandlerContextMap.remove(key);
            if (Objects.nonNull(channelHandlerContextMap)) {
                channelHandlerContext.close();
            }
        }
    }

    public static ChannelHandlerContext get(String key) {
        return channelHandlerContextMap.get(key);
    }
}
