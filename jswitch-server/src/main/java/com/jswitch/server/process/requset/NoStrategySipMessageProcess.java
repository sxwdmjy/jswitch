package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.sip.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("NOSTRATEGY")
public class NoStrategySipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public void handler(SipMessageEvent event) {

    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }
}
