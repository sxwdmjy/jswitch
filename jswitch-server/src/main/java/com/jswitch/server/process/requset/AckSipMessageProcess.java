package com.jswitch.server.process.requset;

import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.channel.SipCallChannel;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.sip.SipRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component("ACK")
public class AckSipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public void handle(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest)event.getMessage();
        SipCallChannel callInfo = SipChannelCache.getCallInfo(sipRequest.getCallId());
        if(Objects.isNull(callInfo)){
            return;
        }
        sendResponse(SessionChannelManager.get(callInfo.getCallee()),sipRequest.toString());
    }
}
