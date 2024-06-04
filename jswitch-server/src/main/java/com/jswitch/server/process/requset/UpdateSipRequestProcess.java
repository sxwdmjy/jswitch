package com.jswitch.server.process.requset;

import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import org.springframework.stereotype.Component;

@Component("UPDATE")
public class UpdateSipRequestProcess extends AbstractSipRequestProcess {
    @Override
    public SipResponse handle(SipRequest message) {
        return new SipResponse();
    }
}
