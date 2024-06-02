package com.jswitch.server.process;

import com.jswitch.server.utils.DigestAuthUtils;
import com.jswitch.server.utils.IpUtils;
import com.jswitch.sip.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {

    @Override
    public String handle(SipRequest message) {
        // 提取Authorization头字段
        String authHeader = message.getHeaders().get("Authorization");
        if (authHeader != null) {
            Map<String, String> authParams = parseAuthorizationHeader(authHeader);
            String username = authParams.get("username");
            String realm = authParams.get("realm");
            String nonce = authParams.get("nonce");
            String uri = authParams.get("uri");
            String response = authParams.get("response");
            String method = "REGISTER";

            // TODO: 从数据库或其他存储中获取用户密码
            String password = getPasswordForUser(username);
            String calculatedResponse = DigestAuthUtils.calculateResponse(username, realm, password, nonce, uri, method);

            if (calculatedResponse.equals(response)) {
                log.info("Authentication successful for user: " + username);
                // 处理注册请求
                return createOkResponse(message);
            } else {
                log.info("Authentication failed for user: " + username);
                return createUnauthorizedResponse(message);
            }
        } else {
            //返回401鉴权
            return createUnauthorizedResponse(message);
        }
    }

    private String createOkResponse(SipRequest request) {

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
        String realm = IpUtils.getLocalHost().getHostAddress();

        SipResponse sipResponse = new SipResponse();
        sipResponse.setSipVersion("SIP/2.0");
        sipResponse.setStatusCode(SipResponseStatus.OK.getStatusCode());
        sipResponse.setReasonPhrase(SipResponseStatus.OK.getReasonPhrase());
        via.setReceived(IpUtils.getLocalHost().getHostAddress());
        via.setRport("1212");
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


    private String getPasswordForUser(String username) {
        // TODO: 从数据库或其他存储中获取用户密码
        return "123456"; // 这是示例密码，请替换为实际存储的密码
    }

    private String createUnauthorizedResponse(SipRequest request) {
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
        via.setReceived(IpUtils.getLocalHost().getHostAddress());
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
