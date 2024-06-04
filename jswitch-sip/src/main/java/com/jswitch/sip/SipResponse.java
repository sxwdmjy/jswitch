package com.jswitch.sip;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SipResponse extends SipMessage {

    private String sipVersion;
    private int statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private SDPMessage body;
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

    public SipResponse(String message) {
        this.headers = new LinkedHashMap<>();
        this.parse(message);
    }

    public void parse(String message) {
        String[] lines = message.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("SIP/2.0")) {
                String[] split = line.split(" ");
                sipVersion = split[0];
                statusCode = Integer.parseInt(split[1]);
                reasonPhrase = split[2];
            } else if (line.startsWith("Via:")) {
                via = new ViaHeader(line.substring(4));
            } else if (line.startsWith("From:")) {
                from = new SipAddress(line.substring(5));
            } else if (line.startsWith("To:")) {
                to = new SipAddress(line.substring(3));
            } else if (line.startsWith("Call-ID:")) {
                callId = line.substring(9);
            } else if (line.startsWith("CSeq:")) {
                cSeq = line.substring(5);
            } else if (line.startsWith("Contact:")) {
                contact = new SipAddress(line.substring(8));
            } else if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring(16));
            }
        }
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
        if (body != null) {
            response.append("\r\n").append(body);
        }
        response.append("\r\n");
        return response.toString();
    }

}
