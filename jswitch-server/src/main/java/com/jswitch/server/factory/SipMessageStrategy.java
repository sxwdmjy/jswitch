package com.jswitch.server.factory;

import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.sip.SipResponse;

public interface SipMessageStrategy {
    SipResponse handle(SipMessageRequest message);
}
