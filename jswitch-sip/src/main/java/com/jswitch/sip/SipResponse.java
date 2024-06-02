package com.jswitch.sip;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SipResponse {

    private String sipVersion;
    private int statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private String body;
    private SipAddress from;
    private SipAddress to;
    private String callId;
    private String cSeq;
    private SipAddress contact;
    private ViaHeader via;
    private int contentLength;

    public SipResponse() {
        this.headers = new LinkedHashMap<>();
    }

    public void putHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append(sipVersion).append(" ").append(statusCode).append(" ").append(reasonPhrase).append("\r\n");
        response.append("Via: ").append(via.toString()).append("\r\n");
        response.append("From: ").append(from.toString()).append("\r\n");
        response.append("To: ").append(to.toString()).append("\r\n");
        response.append("Call-ID: ").append(callId).append("\r\n");
        response.append("CSeq: ").append(cSeq).append("\r\n");
        if (contact != null) {
            response.append("Contact: ").append(contact.toString()).append("\r\n");
        }
        if (contentLength >= 0) {
            response.append("Content-Length: ").append(contentLength).append("\r\n");
        }
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        if (body != null){
            response.append("\r\n").append(body);
        }
        response.append("\r\n");
        return response.toString();
    }

}
