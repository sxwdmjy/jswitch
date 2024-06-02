package com.jswitch.server.process;

import com.jswitch.sip.SipRequest;
import org.springframework.stereotype.Component;

@Component("UPDATE")
public class UpdateSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(SipRequest message) {
        return "";
    }
}
