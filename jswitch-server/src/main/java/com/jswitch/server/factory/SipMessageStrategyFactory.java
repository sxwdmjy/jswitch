package com.jswitch.server.factory;

import com.jswitch.common.annotation.EventName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class SipMessageStrategyFactory {

    @Autowired
    private Map<String, SipMessageStrategy> sipMessageMap;


    public SipMessageStrategy getSipRequestStrategy(String messageType) {
        return sipMessageMap.get(messageType);
    }

    public SipMessageStrategy getSipResponseStrategy(int statusCode) {
        SipMessageStrategy handler = getResponseStrategy(statusCode);
        if (handler != null) return handler;
        return sipMessageMap.get(statusCode);
    }

    private SipMessageStrategy getResponseStrategy(int statusCode) {
        for (SipMessageStrategy handler : sipMessageMap.values()) {
            EventName statusName = handler.getClass().getAnnotation(EventName.class);
            if (statusName == null) {
                statusName = handler.getClass().getSuperclass().getAnnotation(EventName.class);
            }
            if (statusName == null) {
                continue;
            }
            if (Objects.equals(statusCode, statusName.value())) {
                return handler;
            }
        }
        return null;
    }
}
