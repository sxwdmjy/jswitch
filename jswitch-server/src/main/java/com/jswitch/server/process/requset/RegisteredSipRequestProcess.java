package com.jswitch.server.process.requset;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.service.domain.Location;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component("REGISTER")
public class RegisteredSipRequestProcess extends AbstractSipRequestProcess {

    @Override
    public SipResponse handle(SipRequest message) {

        if(checkAuthorization(message)){
            // 处理注册请求
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
            location.setChannelId(message.getChannelId());
            if(StringUtils.hasLength(expires)){
                location.setExpires(DateUtil.offsetSecond(new Date(),Integer.parseInt(expires)));
            }
            if(StringUtils.hasLength(expires) && ObjectUtil.notEqual("0",expires)){
                locationService.add(location);
            } else {
                locationService.delete(location);
            }
            return createOkResponse(message);
        }else {
            return createUnauthorizedResponse(message);
        }
    }







}
