package com.jswitch.server.factory;

import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;

public interface SipResponseStrategy {
    SipRequest handle(SipResponse message);
}
