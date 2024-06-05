package com.jswitch.server.process.requset;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.service.domain.Location;
import com.jswitch.sip.SipRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {

    @Override
    public void handle(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest)event.getMessage();
        if(checkAuthorization(sipRequest)){
            // 处理注册请求
            String expires = sipRequest.getHeaders().get("Expires");
            //注册
            Location location = new Location();
            location.setContact(sipRequest.getContact().toString());
            location.setCallId(sipRequest.getCallId());
            location.setCseq(Integer.parseInt(sipRequest.getCSeq().split(" ")[0]));
            location.setUsername(extractUsername(sipRequest.getFrom().getUri()));
            location.setDomain(sipRequest.getUri().getHost());
            location.setReceived(sipRequest.getRemoteIp());
            location.setStatus(0);
            if(StringUtils.hasLength(expires)){
                location.setExpires(DateUtil.offsetSecond(new Date(),Integer.parseInt(expires)));
            }
            if(StringUtils.hasLength(expires) && ObjectUtil.notEqual("0",expires)){
                SessionChannelManager.add(location.getUsername(), event.getCtx());
                locationService.add(location);
            } else {
                SessionChannelManager.remove(location.getUsername());
                locationService.delete(location);
            }
            sendResponse(event.getCtx(),createOkResponse(sipRequest).toString());
        }else {
            sendResponse(event.getCtx(),createUnauthorizedResponse(sipRequest).toString());
        }
    }







}
