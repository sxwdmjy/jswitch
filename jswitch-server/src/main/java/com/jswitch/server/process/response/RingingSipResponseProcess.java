package com.jswitch.server.process.response;

import cn.hutool.core.util.IdUtil;
import com.jswitch.common.annotation.EventName;
import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.channel.SipCallChannel;
import com.jswitch.server.channel.SipCallDetailChannel;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.sip.SipResponse;
import com.jswitch.sip.SipResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author danmo
 * @date 2024-06-04 11:44
 **/
@Slf4j
@EventName(180)
@Component
public class RingingSipResponseProcess extends AbstractSipMessageProcess {
    @Override
    public void handle(SipMessageEvent event) {
        SipResponse sipResponse = (SipResponse) event.getMessage();
        log.info("RingingSipResponseProcess {}={}",sipResponse.getStatusCode(),sipResponse.getReasonPhrase());

    }
}
