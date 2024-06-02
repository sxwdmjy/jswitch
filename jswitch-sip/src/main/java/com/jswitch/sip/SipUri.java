package com.jswitch.sip;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Data
public class SipUri {

    private static final Pattern SIP_URI_PATTERN = Pattern.compile("<sip:(.+?)>");
    private static final Pattern TAG_PATTERN = Pattern.compile("tag=(.+)");

    private String scheme;
    private String userInfo;
    private String host;
    private Integer port;
    private String transport;
    private Map<String, String> parameters;

    public SipUri(String rawUri) {
        this.parameters = new HashMap<>();
        parse(rawUri);
    }

    private void parse(String rawUri) {
        String[] parts = rawUri.split(";", 2);
        String mainPart = parts[0];
        if (parts.length > 1) {
            String[] paramPairs = parts[1].split(";");
            for (String param : paramPairs) {
                String[] keyValue = param.split("=");
                parameters.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : null);
            }
        }

        String[] mainParts = mainPart.split(":");
        this.scheme = mainParts[0];
        String userInfoAndHost = mainParts[1];
        String[] userInfoAndHostParts = userInfoAndHost.split("@");
        if (userInfoAndHostParts.length == 2) {
            this.userInfo = userInfoAndHostParts[0];
            this.host = userInfoAndHostParts[1].split(":")[0];
        } else {
            this.host = userInfoAndHostParts[0].split(":")[0];
        }
        this.port = Integer.parseInt(mainParts[2]);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(scheme + ":" + (userInfo != null ? userInfo + "@" : "") + host);
        if (port != 0) {
            sb.append(":").append(port);
        }
        if (!parameters.isEmpty()) {
            sb.append(";");
            parameters.forEach((key, value) -> sb.append(key).append("=").append(value).append(";"));
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SipUri sipUri = new SipUri("sip:127.0.0.1:5060;transport=tcp");
        System.out.println(sipUri);
    }
}
