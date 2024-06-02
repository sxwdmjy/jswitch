package com.jswitch.sip;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class SipAddress {

    private String displayName;
    private String uri;
    private Map<String, String> parameters;

    public SipAddress() {
        this.parameters = new HashMap<>();
    }

    public SipAddress(String rawAddress) {
        this.parameters = new HashMap<>();
        parse(rawAddress);
    }

    public void putParameter(String name, String value) {
        parameters.put(name, value);
    }

    private void parse(String rawAddress) {
        int start = rawAddress.indexOf('<');
        int end = rawAddress.indexOf('>');

        // 解析 displayName 和 URI
        if (start != -1 && end != -1) {
            this.displayName = rawAddress.substring(0, start).trim().replace("\"", "");
            this.uri = rawAddress.substring(start + 1, end).trim();
            parseParameters(rawAddress.substring(end + 1).trim());
        } else {
            this.uri = rawAddress.trim();
        }
    }

    private void parseParameters(String parametersPart) {
        String[] parts = parametersPart.split(";");
        for (String part : parts) {
            if (part.contains("=")) {
                String[] paramParts = part.split("=", 2);
                String paramName = paramParts[0].trim();
                String paramValue = paramParts[1].trim();
                parameters.put(paramName, paramValue);
            } else {
                parameters.put(part.trim(), null);
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (displayName != null && !displayName.isEmpty()) {
            sb.append("\"").append(displayName).append("\" ");
        }
        sb.append("<").append(uri).append(">");
        if (!parameters.isEmpty()) {
            parameters.forEach((key, value) -> {
                if(StringUtils.hasLength(key)){
                    sb.append(";").append(key);
                    if (value != null) {
                        sb.append("=").append(value);
                    }
                }
            });
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SipAddress from = new SipAddress("\"1000\" <sip:1000@127.0.0.1>;tag=cfc9acc168b742908087c7cc67e97183");
        SipAddress contact = new SipAddress("\"1000\" <sip:1000@127.0.0.1:59494;transport=TCP;ob>;reg-id=1;+sip.instance=\"<urn:uuid:00000000-0000-0000-0000-0000e42d1d81>\"");
        System.out.println(from);
        System.out.println(contact);
    }
}
