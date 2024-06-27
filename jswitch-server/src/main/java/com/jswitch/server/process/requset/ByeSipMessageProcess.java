package com.jswitch.server.process.requset;

import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.channel.SipCallChannel;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.sip.SipRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("BYE")
public class ByeSipMessageProcess extends AbstractSipMessageProcess {
    @Override
    public void handle(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest)event.getMessage();

    }
}
