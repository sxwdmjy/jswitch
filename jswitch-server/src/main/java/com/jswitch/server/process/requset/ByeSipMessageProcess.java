package com.jswitch.server.process.requset;

import com.jswitch.server.cache.SipDialogManageCache;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.sip.Response;
import com.jswitch.sip.SipRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("BYE")
public class ByeSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handler(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest)event.getMessage();
        if(Objects.nonNull(SipDialogManageCache.getSipDialog(sipRequest.getDialogId(true)))){
            SipDialogManageCache.removeSipDialog(sipRequest.getDialogId(true));
        }
    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }


}
