package com.jswitch.server.factory;

import com.jswitch.sip.SipRequest;

public interface SipMessageStrategy {
    String handle(SipRequest message);
}
