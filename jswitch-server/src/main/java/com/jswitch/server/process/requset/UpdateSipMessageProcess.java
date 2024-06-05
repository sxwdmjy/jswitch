package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import org.springframework.stereotype.Component;

@Component("UPDATE")
public class UpdateSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handle(SipMessageEvent event) {
    }
}
