package com.jswitch.server.process;

import com.jswitch.server.factory.SipMessageStrategy;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractSipMessageProcess implements SipMessageStrategy {
    private static final Pattern SIP_URI_PATTERN = Pattern.compile("<sip:(.+?)>");
    private static final Pattern TAG_PATTERN = Pattern.compile("tag=(.+)");

    private static final Pattern VIA_PATTERN = Pattern.compile("SIP/2\\.0/([A-Z]+) ([^;]+)(;.*)?");

    private static final Pattern AUTH_PARAM_PATTERN = Pattern.compile("(\\w+)=((\"[^\"]*\")|([^,]*))");


    protected Map<String, String> tagMap = new ConcurrentHashMap<>();

    protected String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    protected String extractAuthorizationHeader(String message) {
        String[] lines = message.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("Authorization:")) {
                return line.substring(14).trim();
            }
        }
        return null;
    }

    protected String extractHeaderValue(String message, String headerName) {
        String[] lines = message.split("\r\n");
        for (String line : lines) {
            if (line.startsWith(headerName + ":")) {
                return line.substring(headerName.length() + 1).trim();
            }
        }
        return null;
    }

    protected int extractCSeqValue(String message) {
        String cseqHeader = extractHeaderValue(message, "CSeq");
        if (cseqHeader != null) {
            String[] parts = cseqHeader.split(" ");
            if (parts.length > 0) {
                return Integer.parseInt(parts[0]);
            }
        }
        return -1;
    }

    protected String extractCallId(String message) {
        return extractHeaderValue(message, "Call-ID");
    }
    protected String extractVia(String message) {
        return extractHeaderValue(message, "Via");
    }

    protected String getTagId(String callId) {
        String tag = tagMap.get(callId);
        if (tag == null) {
            tag = generateNonce();
            tagMap.put(callId, tag);
        }
        return tag;
    }

    public static Map<String, String> parseHeaders(String message) {
        Map<String, String> headers = new HashMap<>();
        String[] lines = message.split("\r\n");
        for (String line : lines) {
            if (line.contains(":")) {
                int index = line.indexOf(":");
                String headerName = line.substring(0, index).trim();
                String headerValue = line.substring(index + 1).trim();
                headers.put(headerName, headerValue);
            }
        }
        return headers;
    }

    public static List<Map<String,Object>> parseViaHeaders(String viaHeader) {
        List<Map<String,Object>> viaHeaders = new ArrayList<>();
        String[] viaEntries = viaHeader.split(",");
        for (String viaEntry : viaEntries) {
            Matcher matcher = VIA_PATTERN.matcher(viaEntry.trim());
            if (matcher.matches()) {
                String transport = matcher.group(1);
                String sentBy = matcher.group(2);
                String params = matcher.group(3);
                Map<String,Object> paramsMap = Arrays.stream(params.split(";")).filter(StringUtils::hasLength).collect(Collectors.toMap(item -> item.contains("=") ? item.split("=")[0]: item, item -> item.contains("=") ? item.split("=")[1]: "" ));
                Map<String,Object> via = new HashMap<>();
                via.put("transport",transport);
                via.put("sentBy",sentBy);
                via.put("params",paramsMap);
                viaHeaders.add(via);
            }
        }
        return viaHeaders;
    }

    public static String extractSipUri(String headerValue) {
        Matcher matcher = SIP_URI_PATTERN.matcher(headerValue);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractTag(String headerValue) {
        Matcher matcher = TAG_PATTERN.matcher(headerValue);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
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
