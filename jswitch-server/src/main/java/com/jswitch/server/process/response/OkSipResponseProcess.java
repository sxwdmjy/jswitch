package com.jswitch.server.process.response;

import com.jswitch.common.annotation.EventName;
import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.channel.SipCallChannel;
import com.jswitch.server.channel.SipCallDetailChannel;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author danmo
 * @date 2024-06-04 11:44
 **/
@Slf4j
@EventName(200)
@Component
public class OkSipResponseProcess extends AbstractSipMessageProcess {
    @Override
    public void handle(SipMessageEvent event) {
        SipResponse sipResponse = (SipResponse) event.getMessage();
        log.info("OkSipResponseProcess {}={}",sipResponse.getStatusCode(),sipResponse.getReasonPhrase());
        SipCallChannel callInfo = SipChannelCache.getCallInfo(sipResponse.getCallId());
        SipCallDetailChannel fromChannel = callInfo.getChannelMap().get(callInfo.getFromChannelId());
        callInfo.setStatus(3);
        SipChannelCache.saveSipCallChannel(callInfo);
        //180 Ringing 通知呼叫者，被叫方正在振铃
        sendResponse(SessionChannelManager.get(callInfo.getCaller()),sipResponse.toString());
    }
}
