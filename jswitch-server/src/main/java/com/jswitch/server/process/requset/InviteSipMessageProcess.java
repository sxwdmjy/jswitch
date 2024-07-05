package com.jswitch.server.process.requset;

import com.jswitch.server.cache.SipDialogManageCache;
import com.jswitch.server.cache.TransactionManageCache;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.transaction.ServerSipTransaction;
import com.jswitch.server.transaction.ServerTransactionUser;
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
        SipRequest sipRequest = (SipRequest) event.getMessage();
        ServerTransactionUser serverTransactionUser = new ServerTransactionUser(new ServerSipTransaction());
        serverTransactionUser.setSipMessageStrategy(this);
        serverTransactionUser.getServerTransaction().processRequest(event);
        TransactionManageCache.addServerTransaction(sipRequest.getTransactionId(), serverTransactionUser.getServerTransaction());
    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest) event.getMessage();

        if (checkAuthorization(sipRequest)) {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.TRYING.getStatusCode());
            RecordRouteList recordRouteHeaders = sipRequest.getRecordRouteHeaders();
            response.addHeader(recordRouteHeaders);
            Contact contact = new Contact();
            GenericURI uri = sipRequest.getRequestLine().getUri();
            AddressImpl address = new AddressImpl();
            address.setAddess(uri);
            contact.setAddress(address);
            response.addHeader(contact);

            //sendResponse(event.getCtx(), response);

            if (!SipDialogManageCache.isExist(sipRequest.getDialogId(true))) {
                SipDialog sipDialog = new SipDialog();
                sipDialog.setDialogId(sipRequest.getDialogId(true));
                sipDialog.setState(DialogState.EARLY);
                sipDialog.setCallId(sipRequest.getCallId());
                sipDialog.setRouteList(sipRequest.getRouteHeaders());
                sipDialog.setRemoteTarget(address);
                sipDialog.setRemoteCSeq(sipRequest.getCSeq());
                sipDialog.setLocalCSeq(null);
                sipDialog.setLocalTag(sipRequest.getToTag());
                sipDialog.setRemoteTag(sipRequest.getFrom().getTag());
                SipDialogManageCache.saveSipDialog(sipDialog);
            }
            return response;
        } else {
            return sendUnauthorizedResponse(sipRequest);
        }
    }

}
