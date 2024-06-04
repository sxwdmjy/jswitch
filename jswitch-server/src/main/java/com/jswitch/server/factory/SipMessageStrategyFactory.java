package com.jswitch.server.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SipMessageStrategyFactory {

    @Autowired
    private Map<String, SipRequestStrategy> sipRequestMap;


    public SipRequestStrategy getSipRequestStrategy(String messageType) {
        return sipRequestMap.get(messageType);
    }


}
