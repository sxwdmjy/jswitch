package com.jswitch.server.process.response;

import com.jswitch.common.annotation.EventName;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.sip.Response;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author danmo
 * @date 2024-06-04 11:44
 **/
@Slf4j
@EventName(100)
@Component
public class TryingSipResponseProcess extends AbstractSipMessageProcess {
    @Override
    public void handler(SipMessageEvent event) {
        SipResponse sipResponse = (SipResponse) event.getMessage();
        log.info("TryingSipResponseProcess {}={}",sipResponse.getStatusCode(),sipResponse.getReasonPhrase());

    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }
}
