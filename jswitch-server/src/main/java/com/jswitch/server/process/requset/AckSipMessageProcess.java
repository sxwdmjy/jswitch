package com.jswitch.server.process.requset;

import com.jswitch.server.cache.SipDialogManageCache;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.sip.SipDialog;
import com.jswitch.sip.SipRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component("ACK")
public class AckSipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public void handler(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest) event.getMessage();
        SipDialog sipDialog = SipDialogManageCache.getSipDialog(sipRequest.getDialogId(true));
        if(Objects.nonNull(sipDialog)){

        }
    }

    @Override
    public void handleRequest(SipMessageEvent event) {
        return null;
    }
}
