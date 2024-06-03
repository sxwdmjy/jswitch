package com.jswitch.server.process;

import com.jswitch.common.utils.AESUtils;
import com.jswitch.common.utils.DigestAuthUtils;
import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.service.domain.Subscriber;
import com.jswitch.service.service.ISubscriberService;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {

    @Resource
    private ISubscriberService subscriberService;

    @Override
    public SipResponse handle(SipMessageRequest message) {
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

            //从数据库或其他存储中获取用户密码
            String password = getPasswordForUser(username);
            String calculatedResponse = DigestAuthUtils.calculateResponse(username, realm, password, nonce, uri, method);

            if (calculatedResponse.equals(response)) {
                log.info("Authentication successful for user: " + username);
                // 处理注册请求
                //todo 存储注册信息到数据库
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




    private String getPasswordForUser(String username) {
        Subscriber subscriber = subscriberService.getUserByUsername(username);
        try {
          return AESUtils.decrypt(subscriber.getPassword());
        } catch (Exception e) {
            return "";
        }
    }



}
