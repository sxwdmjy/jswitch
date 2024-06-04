package com.jswitch.server.process.response;

import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;

/**
 * @author danmo
 * @date 2024-06-04 11:44
 **/
public class OkSipResponseProcess extends AbstractSipRequestProcess {

    @Override
    public SipRequest handle(SipResponse message) {
        return super.handle(message);
    }
}
