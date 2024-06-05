package com.jswitch.server.cache;

import com.jswitch.server.channel.SipCallChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author danmo
 * @date 2024-06-05 13:52
 **/
public class SipChannelCache {

    private static final Map<String, SipCallChannel> channelMap = new ConcurrentHashMap<>();


    public static void saveSipCallChannel(SipCallChannel callChannel) {
        channelMap.put(callChannel.getCallId(), callChannel);
    }

    public static SipCallChannel getCallInfo(String callId) {
        return channelMap.get(callId);
    }

    public static void removeCallInfo(String callId) {
        channelMap.remove(callId);
    }
}
