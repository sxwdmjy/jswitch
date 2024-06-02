package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import org.springframework.stereotype.Component;

@Component("OPTIONS")
public class OptionsSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(String message) {
        return "";
    }
}
