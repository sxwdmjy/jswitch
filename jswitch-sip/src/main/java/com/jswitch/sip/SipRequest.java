package com.jswitch.sip;

import com.jswitch.sip.utils.SDPParser;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SipRequest {

    private String method;
    private SipUri uri;
    private String sipVersion;
    private SDPMessage body;

    private SipAddress from;
    private SipAddress to;
    private String callId;
    private String cSeq;
    private SipAddress contact;
    private ViaHeader via;
    private int contentLength;

    private Map<String, String> headers;

    public SipRequest(String message) {
        this.headers = new LinkedHashMap<>();
        parse(message);
    }

    private void parse(String message) {
        String[] lines = message.split("\r\n");

        // 解析请求行
        String[] requestLineParts = lines[0].split(" ", 3);
        if (requestLineParts.length != 3) {
            throw new IllegalArgumentException("Invalid SIP request line: " + lines[0]);
        }
        setMethod(requestLineParts[0]);
        setUri(new SipUri(requestLineParts[1]));
        setSipVersion(requestLineParts[2]);

        // 解析头字段
        int lineIndex = 1;
        while (lineIndex < lines.length && !lines[lineIndex].isEmpty()) {
            String[] headerParts = lines[lineIndex].split(":", 2);
            if (headerParts.length == 2) {
                String headerName = headerParts[0].trim();
                String headerValue = headerParts[1].trim();
                addHeader(headerName, headerValue);
            }
            lineIndex++;
        }
        // 解析主体
        StringBuilder body = new StringBuilder();
        for (int i = lineIndex + 1; i < lines.length; i++) {
            body.append(lines[i]).append("\r\n");
        }
        setBody(SDPParser.parse(body.toString().trim()));
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
        switch (name.toLowerCase()) {
            case "from":
                setFrom(new SipAddress(value));
                break;
            case "to":
                setTo(new SipAddress(value));
                break;
            case "call-id":
                setCallId(value);
                break;
            case "cseq":
                setCSeq(value);
                break;
            case "contact":
                setContact(new SipAddress(value));
                break;
            case "via":
                setVia(new ViaHeader(value));
                break;
            case "content-length":
                setContentLength(Integer.parseInt(value));
                break;
            case "uri":
                setUri(new SipUri(value));
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder request = new StringBuilder();
        request.append(method).append(" ").append(uri).append(" ").append(sipVersion).append("\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        request.append("\r\n").append(body).append("\r\n\r\n");
        return request.toString();
    }

    public static void main(String[] args) {
        String message = "REGISTER sip:127.0.0.1:5060;transport=tcp SIP/2.0\r\n" +
                "Via: SIP/2.0/TCP 127.0.0.1:59494;rport;branch=z9hG4bKPj88eefc7d74864ff4bd59c339be658994;alias\r\n" +
                "Max-Forwards: 70\r\n" +
                "From: \"1000\" <sip:1000@127.0.0.1>;tag=cfc9acc168b742908087c7cc67e97183\r\n" +
                "To: \"1000\" <sip:1000@127.0.0.1>\r\n" +
                "Call-ID: 4e7eac6310b84b2da8b92da702e90702\r\n" +
                "CSeq: 60044 REGISTER\r\n" +
                "User-Agent: MicroSIP/3.21.3\r\n" +
                "Supported: outbound, path\r\n" +
                "Contact: \"1000\" <sip:1000@127.0.0.1:59494;transport=TCP;ob>;reg-id=1;+sip.instance=\"<urn:uuid:00000000-0000-0000-0000-0000e42d1d81>\"\r\n" +
                "Expires: 300\r\n" +
                "Allow: PRACK, INVITE, ACK, BYE, CANCEL, UPDATE, INFO, SUBSCRIBE, NOTIFY, REFER, MESSAGE, OPTIONS\r\n" +
                "Content-Length:  0\r\n\r\n";
        SipRequest sipRequest = new SipRequest(message);
        System.out.println(sipRequest);
    }

}
