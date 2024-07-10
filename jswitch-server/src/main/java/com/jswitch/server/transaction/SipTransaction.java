package com.jswitch.server.transaction;

import com.jswitch.sip.Response;

public interface SipTransaction extends Transaction {

    void processRequest();

    void sendResponse(Response response);

}
