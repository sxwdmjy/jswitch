package com.jswitch.server.process;

import com.jswitch.sip.SipRequest;
import org.springframework.stereotype.Component;

@Component("CANCEL")
public class CancelSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(SipRequest message) {
        return "";
    }
}
