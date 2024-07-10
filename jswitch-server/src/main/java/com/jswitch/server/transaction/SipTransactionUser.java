package com.jswitch.server.transaction;

import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.requset.InviteSipMessageProcess;
import com.jswitch.sip.Response;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @author danmo
 * @date 2024-07-04 14:23
 **/
@Data
public class SipTransactionUser implements TransactionUser {

    private SipMessageStrategy strategy;

    public SipTransactionUser(SipMessageStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void onRequest(SipRequest request, ServerSipTransaction transaction) {

    }

    @Override
    public void onResponse(SipResponse response, ClientSipTransaction transaction) {

    }

    @Override
    public void onTimeout(Transaction transaction) {

    }

    @Override
    public void onCancel(Transaction transaction) {

    }

    @Override
    public void sendRequest(SipRequest request, ChannelHandlerContext ctx) {
        strategy.handleRequest(new SipMessageEvent(request, ctx));
    }

    @Override
    public void sendResponseError(SipMessageEvent event) {

    }
}
