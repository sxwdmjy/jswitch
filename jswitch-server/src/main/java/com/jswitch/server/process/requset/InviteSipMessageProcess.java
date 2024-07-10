package com.jswitch.server.process.requset;

import com.jswitch.server.cache.TransactionManageCache;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.transaction.ServerSipTransaction;
import com.jswitch.server.transaction.SipTransactionUser;
import com.jswitch.service.service.ILocationService;
import com.jswitch.sip.*;
import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.header.Contact;
import com.jswitch.sip.header.RecordRouteList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("INVITE")
public class InviteSipMessageProcess extends AbstractSipMessageProcess {

    @Resource
    private ILocationService locationService;

    @Override
    public void handler(SipMessageEvent event) throws InterruptedException {
        SipTransactionUser sipTransactionUser = new SipTransactionUser(this);
        SipRequest sipRequest = (SipRequest) event.getMessage();
        ServerSipTransaction serverSipTransaction = new ServerSipTransaction(event, sipTransactionUser);
        serverSipTransaction.processRequest();
        TransactionManageCache.addServerTransaction(serverSipTransaction.getTransactionId(), serverSipTransaction);
    }
    @Override
    public void handleRequest(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest) event.getMessage();

        if (checkAuthorization(sipRequest)) {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.RINGING.getStatusCode());
            RecordRouteList recordRouteHeaders = sipRequest.getRecordRouteHeaders();
            response.addHeader(recordRouteHeaders);
            Contact contact = new Contact();
            GenericURI uri = sipRequest.getRequestLine().getUri();
            AddressImpl address = new AddressImpl();
            address.setAddess(uri);
            contact.setAddress(address);
            response.addHeader(contact);
            sendResponse(event.getCtx(), response);
            //return response;
        } else {
            sendResponse(event.getCtx(), sendUnauthorizedResponse(sipRequest));
        }
    }

}
