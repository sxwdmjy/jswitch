package com.jswitch.server.process;

import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.sip.SipResponse;
import org.springframework.stereotype.Component;

@Component("MESSAGE")
public class MessageSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public SipResponse handle(SipMessageRequest message) {
        return new SipResponse();
    }
}