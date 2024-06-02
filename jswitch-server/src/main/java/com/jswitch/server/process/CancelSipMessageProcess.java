package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import org.springframework.stereotype.Component;

@Component("CANCEL")
public class CancelSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(String message) {
        return "";
    }
}
