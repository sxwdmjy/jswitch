package com.jswitch.server.factory;

import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;

public interface SipRequestStrategy {
    SipResponse handle(SipRequest message);
}
