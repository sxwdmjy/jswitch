package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import org.springframework.stereotype.Component;

@Component("PUBLISH")
public class PublishSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handle(SipMessageEvent event) {
    }
}
