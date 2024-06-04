package com.jswitch.server.process.requset;

import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.server.utils.SipChannelManager;
import com.jswitch.service.domain.Location;
import com.jswitch.service.service.ILocationService;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import com.jswitch.sip.SipResponseStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component("INVITE")
public class InviteSipRequestProcess extends AbstractSipRequestProcess {

    @Resource
    private ILocationService locationService;

    @Override
    public SipResponse handle(SipRequest message) {

        //发送401要求身份验证
        if (!checkAuthorization(message)) {
            return createUnauthorizedResponse(message);
        }
        //先发送100,通知发送 INVITE 消息的一方（通常是呼叫者），接收到的请求正在被处理
        SipResponse response = createResponse(SipResponseStatus.TRYING.getStatusCode(), message, SipResponseStatus.TRYING.getReasonPhrase());
        SipChannelManager.get(message.getChannelId()).writeAndFlush(response.toString());
        //优先查找已订阅用户
        String username = extractUsername(message.getTo().getUri());
        Location loaction = locationService.getByUserName(username);
        if(Objects.nonNull(loaction)){
            //邀请呼叫
            sendRequest(message,loaction);
        }
        //180 Ringing 通知呼叫者，被叫方正在振铃


        return createOkResponse(message);
    }

    private void sendRequest(SipRequest message, Location loaction) {
        SipChannelManager.get(loaction.getChannelId()).writeAndFlush(message.toString());
    }
}
