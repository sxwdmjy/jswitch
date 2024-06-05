package com.jswitch.server.process.requset;

import cn.hutool.core.util.IdUtil;
import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.channel.SipCallChannel;
import com.jswitch.server.channel.SipCallDetailChannel;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.service.domain.Location;
import com.jswitch.service.service.ILocationService;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import com.jswitch.sip.SipResponseStatus;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component("INVITE")
public class InviteSipMessageProcess extends AbstractSipMessageProcess {

    @Resource
    private ILocationService locationService;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public void handle(SipMessageEvent event) throws InterruptedException {
        SipRequest sipRequest = (SipRequest) event.getMessage();

        long currented = System.currentTimeMillis();
        SipCallDetailChannel fromChannel = new SipCallDetailChannel();
        fromChannel.setUniqueId(IdUtil.fastSimpleUUID());
        fromChannel.setCallId(sipRequest.getCallId());
        fromChannel.setHost(sipRequest.getFrom().getUri());
        fromChannel.setCallTime(currented);
        fromChannel.setStatus(1);


        SipCallChannel sipCallChannel = new SipCallChannel();
        sipCallChannel.setFromChannelId(sipRequest.getChannelId());
        sipCallChannel.setStatus(1);
        sipCallChannel.setCallId(sipRequest.getCallId());
        sipCallChannel.addMessage(sipRequest);
        sipCallChannel.setFromChannelId(fromChannel.getUniqueId());
        sipCallChannel.setChannelInfoMap(fromChannel.getUniqueId(), fromChannel);
        sipCallChannel.setCallTime(currented);
        sipCallChannel.setCaller(extractUsername(sipRequest.getFrom().getUri()));
        sipCallChannel.setCallee(extractUsername(sipRequest.getTo().getUri()));

        //发送401要求身份验证
        if (!checkAuthorization(sipRequest)) {
            sendResponse(event.getCtx(),createUnauthorizedResponse(sipRequest).toString());
            return;
        }
        //先发送100,通知发送 INVITE 消息的一方（通常是呼叫者），接收到的请求正在被处理
        SipResponse response = createResponse(SipResponseStatus.TRYING.getStatusCode(), sipRequest, SipResponseStatus.TRYING.getReasonPhrase());
        sendResponse(event.getCtx(),response.toString());
        fromChannel.setStatus(2);
        //优先查找已订阅用户
        Location loaction = locationService.getByUserName(sipCallChannel.getCallee());
        if (Objects.nonNull(loaction)) {
            //邀请呼叫
            sendInviteRequest(sipRequest, loaction);
            fromChannel.setStatus(3);
        } else {
            sendResponse(event.getCtx(),createResponse(403, sipRequest, "Forbidden").toString());
            return;
        }
        SipChannelCache.saveSipCallChannel(sipCallChannel);



    }


    private void sendInviteRequest(SipRequest message, Location loaction) {
        SipRequest inviteRequest = createInviteRequest(message);
        sendResponse(SessionChannelManager.get(loaction.getUsername()),inviteRequest.toString());
    }
}
