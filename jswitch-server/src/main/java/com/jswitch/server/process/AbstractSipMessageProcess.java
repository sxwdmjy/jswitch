package com.jswitch.server.process;

import com.jswitch.common.utils.IpUtils;
import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.sip.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    protected String createOkResponse(SipMessageRequest request) {
        // 解析SIP URI和标签
        SipAddress from = request.getFrom();
        SipAddress to = request.getTo();
        String fromUri = from.getUri();
        String fromTag = from.getParameters().get("tag");
        String toUri = to.getUri();
        ViaHeader via = request.getVia();
        String callId = request.getCallId();
        String cseq = request.getCSeq();
        SipAddress contact = request.getContact();
        String tagId = getTagId(callId);
        String nonce = generateNonce();

        SipResponse sipResponse = new SipResponse();
        sipResponse.setSipVersion("SIP/2.0");
        sipResponse.setStatusCode(SipResponseStatus.OK.getStatusCode());
        sipResponse.setReasonPhrase(SipResponseStatus.OK.getReasonPhrase());
        via.setReceived(request.getRemoteIp());
        via.setRport(request.getRemotePort());
        sipResponse.setVia(via);
        to.putParameter("tag", tagId);
        sipResponse.setTo(to);
        sipResponse.setFrom(from);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cseq);
        SipAddress contactAdress = new SipAddress();
        contactAdress.setUri(contact.getUri());
        contactAdress.putParameter("expires", request.getHeaders().get("Expires"));
        sipResponse.setContact(contactAdress);
        sipResponse.putHeader("Server", "Jswitch");
        sipResponse.setContentLength(0);
        return sipResponse.toString();
    }

    protected String createUnauthorizedResponse(SipMessageRequest request) {
        // 解析SIP URI和标签
        SipAddress from = request.getFrom();
        SipAddress to = request.getTo();
        String fromUri = from.getUri();
        String fromTag = from.getParameters().get("tag");
        String toUri = to.getUri();
        ViaHeader via = request.getVia();
        String callId = request.getCallId();
        String cseq = request.getCSeq();
        String tagId = getTagId(callId);
        String nonce = generateNonce();
        String realm = IpUtils.getLocalHost().getHostAddress();

        SipResponse sipResponse = new SipResponse();
        via.setReceived(request.getRemoteIp());
        sipResponse.setSipVersion("SIP/2.0");
        sipResponse.setStatusCode(SipResponseStatus.UNAUTHORIZED.getStatusCode());
        sipResponse.setReasonPhrase(SipResponseStatus.UNAUTHORIZED.getReasonPhrase());
        sipResponse.setVia(via);
        to.putParameter("tag", tagId);
        sipResponse.setTo(to);
        sipResponse.setFrom(from);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cseq);
        sipResponse.putHeader("WWW-Authenticate", "Digest realm=\"" + realm + "\", nonce=\"" + nonce + "\"");
        sipResponse.putHeader("Server", "Jswitch");
        sipResponse.setContentLength(0);

        return sipResponse.toString();
    }
}
