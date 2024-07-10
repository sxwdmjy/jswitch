package com.jswitch.server.factory;

import com.jswitch.server.msg.SipMessageEvent;

public interface SipMessageStrategy {
    void handler(SipMessageEvent event) throws InterruptedException;

    void handleRequest(SipMessageEvent event);
}
