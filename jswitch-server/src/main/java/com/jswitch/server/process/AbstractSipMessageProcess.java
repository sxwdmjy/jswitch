package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractSipMessageProcess implements SipMessageStrategy {


    private static final Pattern AUTH_PARAM_PATTERN = Pattern.compile("(\\w+)=((\"[^\"]*\")|([^,]*))");


    protected Map<String, String> tagMap = new ConcurrentHashMap<>();

    protected String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    protected String getTagId(String callId) {
        String tag = tagMap.get(callId);
        if (tag == null) {
            tag = generateNonce();
            tagMap.put(callId, tag);
        }
        return tag;
    }


    public static Map<String, String> parseAuthorizationHeader(String authHeader) {
        Map<String, String> authParams = new HashMap<>();
        Matcher matcher = AUTH_PARAM_PATTERN.matcher(authHeader);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2).replace("\"", "");
            authParams.put(key, value);
        }
        return authParams;
    }
}
