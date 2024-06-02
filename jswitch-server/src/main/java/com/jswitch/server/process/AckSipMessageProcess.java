package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ACK")
public class AckSipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public String handle(String message) {
        return "";
    }
}
