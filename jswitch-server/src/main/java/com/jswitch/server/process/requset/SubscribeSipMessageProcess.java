package com.jswitch.server.process.requset;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.service.service.ILocationService;
import com.jswitch.sip.*;
import com.jswitch.sip.header.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("SUBSCRIBE")
public class SubscribeSipMessageProcess extends AbstractSipMessageProcess {

    @Resource
    private ILocationService locationService;

    @Override
    public void handler(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest) event.getMessage();
        // 解析请求头和其他必要信息
        ToHeader messageTo = sipRequest.getTo();
        FromHeader messageFrom = sipRequest.getFrom();
        CallIdHeader callId = sipRequest.getCallId();
        CSeqHeader cSeq = sipRequest.getCSeq();
        EventHeader eventHeader = sipRequest.getEventHeader();
        ExpiresHeader expires = sipRequest.getExpires();
        // 检查事件类型
        if (eventHeader == null || !isValidEvent(eventHeader)) {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.BAD_EVENT.getStatusCode());
            event.getCtx().writeAndFlush(response);
        }

        // 检查订阅者身份验证
        if (!isAuthenticated(messageFrom)) {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.UNAUTHORIZED.getStatusCode());
            event.getCtx().writeAndFlush(response);
        }

        // 检查订阅期限
        if (expires.getExpires() <= 0) {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.BAD_REQUEST.getStatusCode());
            event.getCtx().writeAndFlush(response);
        }
        // 如果所有检查通过，生成并发送200 OK响应
        SipResponse response = sipRequest.createResponse(SipResponseStatus.OK.getStatusCode(), "Subscription accepted");
        event.getCtx().writeAndFlush(response);
    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }

    private boolean isAuthenticated(FromHeader messageFrom) {
        String username = messageFrom.getAddress().getDisplayName();
        return locationService.checkUserName(username);
    }

    /**
     * presence：用于呈现用户的在线状态。通常用于即时通讯（IM）系统，以通知其他用户某个用户的在线或离线状态
     * dialog：用于监视对话状态，例如正在进行的通话的开始和结束
     * refer：用于REFER方法，指示资源转移的状态
     * conference：用于会议状态的更新，例如参与者的加入和离开
     * message-summary：用于通知消息等待指示（MWI），如未读的语音邮件
     * reg：用于监视用户注册状态
     *
     * @param eventHeader
     * @return
     */
    private boolean isValidEvent(EventHeader eventHeader) {

        String eventStr = "presence|dialog|refer|conference|message-summary|reg";
        // 检查事件类型是否有效（根据实际需求实现）
        return eventStr.contains(eventHeader.getEventType().toLowerCase());
    }
}
