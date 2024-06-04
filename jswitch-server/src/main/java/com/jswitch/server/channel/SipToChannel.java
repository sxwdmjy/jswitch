package com.jswitch.server.channel;

/**
 * @author danmo
 * @date 2024-06-04 17:28
 **/
public class SipToChannel implements SipChannel{

    private String channelId;

    private String callId;

    private String host;

    @Override
    public String getChannelId() {
        return this.channelId;
    }
}
