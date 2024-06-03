package com.jswitch.server.msg;

import com.jswitch.sip.SipRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author danmo
 * @date 2024-06-03 16:23
 **/
@Setter
@Getter
public class SipMessageRequest extends SipRequest {

    private String channelId;

    private String remoteIp;

    private String remotePort;

    public SipMessageRequest(String message) {
        super(message);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
