package com.jswitch.server.process;

import com.jswitch.server.msg.SipMessageRequest;
import org.springframework.stereotype.Component;

@Component("UPDATE")
public class UpdateSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(SipMessageRequest message) {
        return "";
    }
}
