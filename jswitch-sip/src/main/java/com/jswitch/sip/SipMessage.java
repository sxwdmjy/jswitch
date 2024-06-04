package com.jswitch.sip;

import lombok.Data;

@Data
public abstract class SipMessage {

    private String channelId;

    private String remoteIp;

    private String remotePort;


}
