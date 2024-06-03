package com.jswitch.server.process;

import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ACK")
public class AckSipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public SipResponse handle(SipMessageRequest message) {
        return new SipResponse();
    }
}
