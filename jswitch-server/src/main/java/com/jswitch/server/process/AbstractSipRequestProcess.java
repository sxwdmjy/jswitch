package com.jswitch.server.process;

import com.jswitch.common.utils.AESUtils;
import com.jswitch.common.utils.DigestAuthUtils;
import com.jswitch.server.factory.SipRequestStrategy;
import com.jswitch.server.factory.SipResponseStrategy;
import com.jswitch.service.domain.Subscriber;
import com.jswitch.service.service.ILocationService;
import com.jswitch.service.service.ISubscriberService;
import com.jswitch.sip.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public abstract class AbstractSipRequestProcess implements SipRequestStrategy, SipResponseStrategy {

    @Resource
    protected ISubscriberService subscriberService;

    @Resource
    protected ILocationService locationService;

    private static final Pattern AUTH_PARAM_PATTERN = Pattern.compile("(\\w+)=((\"[^\"]*\")|([^,]*))");
    private static final Pattern SIP_URI_PATTERN = Pattern.compile("sip:([^@]+)@[^;]+");


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

    protected String extractUsername(String sipUri) {
        Matcher matcher = SIP_URI_PATTERN.matcher(sipUri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    protected String getPasswordForUser(String username) {
        Subscriber subscriber = subscriberService.getUserByUsername(username);
        try {
            return AESUtils.decrypt(subscriber.getPassword());
        } catch (Exception e) {
            return "";
        }
    }


    protected Map<String, String> parseAuthorizationHeader(String authHeader) {
        Map<String, String> authParams = new HashMap<>();
        Matcher matcher = AUTH_PARAM_PATTERN.matcher(authHeader);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2).replace("\"", "");
            authParams.put(key, value);
        }
        return authParams;
    }

    //身份验证
    protected Boolean checkAuthorization(SipRequest request) {
        String authHeader = request.getHeaders().get("Authorization");
        if (StringUtils.isBlank(authHeader)) {
            return false;
        }
        Map<String, String> authParams = parseAuthorizationHeader(authHeader);
        String username = authParams.get("username");
        String realm = authParams.get("realm");
        String nonce = authParams.get("nonce");
        String uri = authParams.get("uri");
        String response = authParams.get("response");
        String method = request.getMethod();
        //从数据库或其他存储中获取用户密码
        String password = getPasswordForUser(username);
        String calculatedResponse = DigestAuthUtils.calculateResponse(username, realm, password, nonce, uri, method);
        if(calculatedResponse.equals(response)){
            log.info("Authentication successful for user: " + username);
            return true;
        }else {
            log.info("Authentication failed for user: " + username);
            return false;
        }
    }

    protected SipResponse createTryingResponse(SipRequest request) {
        SipAddress from = request.getFrom();
        SipAddress to = request.getTo();
        String callId = request.getCallId();
        ViaHeader via = request.getVia();
        via.setReceived(request.getRemoteIp());
        via.setRport(request.getRemotePort());
        String cseq = request.getCSeq();
        SipAddress contact = request.getContact();
        String toUri = to.getUri();
        String tagId = getTagId(callId);
        SipResponse sipResponse = new SipResponse();
        sipResponse.setSipVersion("SIP/2.0");
        sipResponse.setStatusCode(SipResponseStatus.TRYING.getStatusCode());
        sipResponse.setReasonPhrase(SipResponseStatus.TRYING.getReasonPhrase());
        sipResponse.setVia(via);
        to.putParameter("tag", tagId);
        sipResponse.setTo(to);
        sipResponse.setFrom(from);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cseq);
        SipAddress contactAdress = new SipAddress();
        contactAdress.setUri(contact.getUri());
        sipResponse.setContact(contactAdress);
        sipResponse.putHeader("Server", "Jswitch");
        sipResponse.setContentLength(0);
        return sipResponse;
    }

    protected SipResponse createOkResponse(SipRequest request) {
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
        if(StringUtils.isNotEmpty(request.getHeaders().get("Expires"))){
            contactAdress.putParameter("expires", request.getHeaders().get("Expires"));
        }
        sipResponse.setContact(contactAdress);
        sipResponse.putHeader("Server", "Jswitch");
        sipResponse.setContentLength(0);
        return sipResponse;
    }

    protected SipResponse createResponse(int statusCode, SipRequest request, String reasonPhrase) {
        SipResponse response = new SipResponse();
        response.setStatusCode(statusCode);
        response.setSipVersion("SIP/2.0");
        response.setReasonPhrase(reasonPhrase);
        ViaHeader via = request.getVia();
        via.setReceived(request.getRemoteIp());
        via.setRport(request.getRemotePort());
        response.setVia(via);
        SipAddress requestTo = request.getTo();
        requestTo.putParameter("tag", getTagId(request.getCallId()));
        response.setTo(requestTo);
        response.setFrom(request.getFrom());
        response.setCallId(request.getCallId());
        response.setCSeq(request.getCSeq());
        response.putHeader("Server", "Jswitch");
        response.setContentLength(0);
        return response;
    }

    protected SipResponse createUnauthorizedResponse(SipRequest request) {
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

        SipResponse sipResponse = new SipResponse();
        via.setReceived(request.getRemoteIp());
        via.setRport(request.getRemotePort());
        sipResponse.setSipVersion("SIP/2.0");
        sipResponse.setStatusCode(SipResponseStatus.UNAUTHORIZED.getStatusCode());
        sipResponse.setReasonPhrase(SipResponseStatus.UNAUTHORIZED.getReasonPhrase());
        sipResponse.setVia(via);
        to.putParameter("tag", tagId);
        sipResponse.setTo(to);
        sipResponse.setFrom(from);
        sipResponse.setCallId(callId);
        sipResponse.setCSeq(cseq);
        sipResponse.putHeader("WWW-Authenticate", "Digest realm=\"" + request.getRemoteIp() + "\", nonce=\"" + nonce + "\"");
        sipResponse.putHeader("Server", "Jswitch");
        sipResponse.setContentLength(0);

        return sipResponse;
    }

    @Override
    public SipResponse handle(SipRequest message) {
        return null;
    }

    @Override
    public SipRequest handle(SipResponse message) {
        return null;
    }
}
