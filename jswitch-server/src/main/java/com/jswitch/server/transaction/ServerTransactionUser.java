package com.jswitch.server.transaction;

import com.jswitch.common.enums.TransactionEventEnum;
import com.jswitch.common.enums.TransactionStateEnum;
import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.sip.Response;
import lombok.Data;
import org.springframework.statemachine.StateMachine;

/**
 * @author danmo
 * @date 2024-07-04 14:23
 **/
@Data
public class ServerTransactionUser implements TransactionUser {

    private final ServerSipTransaction serverTransaction;

    private SipMessageStrategy sipMessageStrategy;

    private StateMachine<TransactionStateEnum, TransactionEventEnum> stateMachine;

    public ServerTransactionUser(ServerSipTransaction serverTransaction) {
        serverTransaction.setTransactionUser(this);
        this.serverTransaction = serverTransaction;
    }

    @Override
    public void handleRequest(SipMessageEvent event) {
        Response response = sipMessageStrategy.handleRequest(event);
        if (response != null) {
            serverTransaction.sendResponse(event);
        }
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
