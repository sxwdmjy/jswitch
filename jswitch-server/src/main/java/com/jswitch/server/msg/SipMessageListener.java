package com.jswitch.server.msg;

public interface SipMessageListener {

    void onMessage(SipMessageEvent event);
}
