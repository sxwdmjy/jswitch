package com.jswitch.server.process;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jswitch.common.utils.AESUtils;
import com.jswitch.common.utils.DigestAuthUtils;
import com.jswitch.server.msg.SipMessageRequest;
import com.jswitch.service.domain.Location;
import com.jswitch.service.domain.Subscriber;
import com.jswitch.service.service.ILocationService;
import com.jswitch.service.service.ISubscriberService;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {

    @Resource
    private ISubscriberService subscriberService;

    @Resource
    private ILocationService locationService;

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
                String expires = message.getHeaders().get("Expires");
                //注册
                Location location = new Location();
                location.setContact(message.getContact().toString());
                location.setCallId(message.getCallId());
                location.setCseq(Integer.parseInt(message.getCSeq().split(" ")[0]));
                location.setUsername(extractUsername(message.getFrom().getUri()));
                location.setDomain(message.getUri().getHost());
                location.setReceived(message.getRemoteIp());
                location.setStatus(0);
                if(StringUtils.hasLength(expires)){
                    location.setExpires(DateUtil.offsetSecond(new Date(),Integer.parseInt(expires)));
                }
                if(StringUtils.hasLength(expires) && ObjectUtil.equals("0",expires)){
                    locationService.saveOrUpdate(location);

                } else {
                    locationService.delete(location);
                }
                return createOkResponse(message);
            } else {
                log.info("Authentication failed for user: " + username);
                return createUnauthorizedResponse(message);
            }
        } else {
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
