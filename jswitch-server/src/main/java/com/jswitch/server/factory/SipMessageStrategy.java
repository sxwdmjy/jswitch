package com.jswitch.server.factory;

import com.jswitch.server.msg.SipMessageEvent;

public interface SipMessageStrategy {
    void handle(SipMessageEvent event) throws InterruptedException;
}
