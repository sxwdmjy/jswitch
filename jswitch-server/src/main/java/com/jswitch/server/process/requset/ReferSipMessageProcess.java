package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import org.springframework.stereotype.Component;

@Component("REFER")
public class ReferSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handler(SipMessageEvent event) {
    }

    @Override
    public void handleRequest(SipMessageEvent event) {
        return null;
    }
}
