package com.jswitch.server.process;

import com.jswitch.common.utils.AESUtils;
import com.jswitch.common.utils.DigestAuthUtils;
import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.service.domain.Subscriber;
import com.jswitch.service.service.ILocationService;
import com.jswitch.service.service.ISubscriberService;
import com.jswitch.sip.DuplicateNameValueList;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import com.jswitch.sip.header.Authorization;
import com.jswitch.sip.header.HeaderFactory;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public abstract class AbstractSipMessageProcess implements SipMessageStrategy {

    @Resource
    protected ISubscriberService subscriberService;

    @Resource
    protected ILocationService locationService;

    @Resource
    protected HeaderFactory headerFactory;


    @Override
    public void handle(SipMessageEvent event) throws InterruptedException {

    }

    //身份验证
    public Boolean checkAuthorization(SipRequest request) {
        Authorization authorization = request.getAuthorization();
        if (Objects.isNull(authorization)) {
            return false;
        }
        String username = authorization.getParameter("username");
        String realm = authorization.getParameter("realm");
        String nonce = authorization.getParameter("nonce");
        String uri = authorization.getParameter("uri");
        String response = authorization.getParameter("response");
        String method = request.getMethod();
        //从数据库或其他存储中获取用户密码
        Subscriber subscriber = subscriberService.getUserByUsername(username);
        String password = "";
        try {
            password =  AESUtils.decrypt(subscriber.getPassword());
        } catch (Exception e) {
            password = "";
        }
        String calculatedResponse = DigestAuthUtils.calculateResponse(username, realm, password, nonce, uri, method);
        if(calculatedResponse.equals(response)){
            log.info("Authentication successful for user: " + username);
            return true;
        }else {
            log.info("Authentication failed for user: " + username);
            return false;
        }
    }
}
