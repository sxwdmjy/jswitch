package com.jswitch.server.channel;

import com.jswitch.sip.SipMessage;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author danmo
 * @date 2024-06-04 17:29
 **/
@Data
public class SipChannelManager {

    private String callId;

    private Integer status;

    private String fromChannelId;
    private String toChannelId;

    private List<SipMessage> sipMessages;

    private static Map<String, SipChannel> sipChannelMap = new ConcurrentHashMap<>();
}
