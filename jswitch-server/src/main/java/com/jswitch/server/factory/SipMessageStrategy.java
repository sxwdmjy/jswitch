package com.jswitch.server.factory;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.sip.Response;

public interface SipMessageStrategy {
    void handler(SipMessageEvent event) throws InterruptedException;

    Response handleRequest(SipMessageEvent event);
}
