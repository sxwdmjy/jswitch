package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import org.springframework.stereotype.Component;


@Component("NOTIFY")
public class NotifySipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public String handle(String message) {
        return "";
    }
}
