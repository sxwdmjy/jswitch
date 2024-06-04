package com.jswitch.server.process.requset;

import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("NOSTRATEGY")
public class NoStrategySipRequestProcess extends AbstractSipRequestProcess {


    @Override
    public SipResponse handle(SipRequest message) {
        return createResponse(500, message, "No Strategy");
    }
}
