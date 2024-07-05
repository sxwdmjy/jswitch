package com.jswitch.server.transaction;

import com.jswitch.server.msg.SipMessageEvent;

public interface SipTransaction extends Transaction {

    void processRequest(SipMessageEvent event);

    void sendResponse(SipMessageEvent event);

}
