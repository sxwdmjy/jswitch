package com.jswitch.server.process;

import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.sip.SipResponse;
import org.springframework.stereotype.Component;

@Component("PUBLISH")
public class PublishSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public SipResponse handle(SipMessageRequest message) {
        return new SipResponse();
    }
}