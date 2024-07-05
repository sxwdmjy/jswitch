package com.jswitch.server.transaction;

import com.jswitch.server.msg.SipMessageEvent;

/**
 * @author danmo
 * @date 2024-07-04 14:23
 **/
public class ClientTransactionUser implements TransactionUser{

    private final ClientSipTransaction clientTransaction;

    public ClientTransactionUser(ClientSipTransaction clientTransaction) {
        this.clientTransaction = clientTransaction;
    }


    @Override
    public void handleRequest(SipMessageEvent event) {

    }

    @Override
    public void sendRequest(SipMessageEvent event) {

    }

    @Override
    public void cancelTransaction(SipMessageEvent event) {

    }

    @Override
    public void sendResponse(SipMessageEvent event) {

    }

    @Override
    public void sendResponseError(SipMessageEvent event) {

    }
}
