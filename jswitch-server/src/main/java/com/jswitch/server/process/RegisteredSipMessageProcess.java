package com.jswitch.server.process;

import com.jswitch.server.utils.DigestAuthUtils;
import com.jswitch.server.utils.IpUtils;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {



    @Override
    public String handle(String message) {
        // 提取Authorization头字段
        String authHeader = extractAuthorizationHeader(message);
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
                System.out.println("Authentication successful for user: " + username);
                // 继续处理注册逻辑
            } else {
                System.out.println("Authentication failed for user: " + username);
            }
        }else {
            //返回401鉴权
            return createUnauthorizedResponse(message);
        }
        return "123";
    }




    private String getPasswordForUser(String username) {
        // TODO: 从数据库或其他存储中获取用户密码
        return "123456"; // 这是示例密码，请替换为实际存储的密码
    }

    private String createUnauthorizedResponse(String msg) {
        // 解析SIP URI和标签
        String from = parseHeaders(msg).get("From");
        String to = parseHeaders(msg).get("To");
        String fromUri = extractSipUri(from);
        String fromTag = extractTag(from);
        String toUri = extractSipUri(to);
        String via = extractVia(msg);
        List<Map<String, Object>> viaList = parseViaHeaders(parseHeaders(msg).get("Via"));
        String callId = extractCallId(msg);
        int cseq = extractCSeqValue(msg);
        String tagId = getTagId(callId);
        String nonce = generateNonce();
        String realm = IpUtils.getLocalHost().getHostAddress();
        return "SIP/2.0 401 Unauthorized\r\n" +
                "Via: "+via+";received="+fromUri.split("@")[1]+"\r\n" +
                "To: "+to+";tag="+tagId+"\r\n" +
                "From: "+from+"\r\n" +
                "Call-ID: " + callId + "\r\n" +
                "CSeq: " + cseq + " REGISTER\r\n" +
                "WWW-Authenticate: Digest realm=\"" + fromUri.split("@")[1] + "\", nonce=\"" + nonce + "\"\r\n" +
                "Server: Jswitch\r\n"+
                "Content-Length: 0\r\n\r\n";
    }

}
