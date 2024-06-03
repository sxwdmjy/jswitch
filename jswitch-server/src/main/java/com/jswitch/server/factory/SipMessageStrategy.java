package com.jswitch.server.factory;

import com.jswitch.server.msg.SipMessageRequest;

public interface SipMessageStrategy {
    String handle(SipMessageRequest message);
}
