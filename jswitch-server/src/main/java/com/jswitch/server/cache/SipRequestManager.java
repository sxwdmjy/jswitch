package com.jswitch.server.cache;

import com.jswitch.service.config.RedisService;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author danmo
 * @date 2024-06-03 18:45
 **/
@Component
public class SipRequestManager {

    @Autowired
    private RedisService redisService;


    public void addSipRequest(SipRequest sipRequest) {

    }

    public void addSipResponse(SipResponse sipResponse) {

    }

    public void removeSipRequest(String sipRequest) {

    }
}
