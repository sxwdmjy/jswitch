package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.sip.Response;
import org.springframework.stereotype.Component;

@Component("OPTIONS")
public class OptionsSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handler(SipMessageEvent event) {
    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }
}
