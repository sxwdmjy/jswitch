package com.jswitch.server.transaction;

import com.jswitch.common.enums.TransactionStateEnum;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.sip.Response;

/**
 * @author danmo
 * @date 2024-07-02 14:26
 **/
public class ClientSipTransaction implements SipTransaction {
    private TransactionUser transactionUser;
    private TransactionStateEnum state;
    @Override
    public void processRequest(SipMessageEvent event) {

    }

    @Override
    public void sendResponse(SipMessageEvent event, Response response) {

    }
}
