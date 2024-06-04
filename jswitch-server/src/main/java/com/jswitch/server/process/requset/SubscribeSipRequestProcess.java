package com.jswitch.server.process.requset;

import com.jswitch.server.process.AbstractSipRequestProcess;
import com.jswitch.service.service.ILocationService;
import com.jswitch.sip.SipAddress;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("SUBSCRIBE")
public class SubscribeSipRequestProcess extends AbstractSipRequestProcess {

    @Resource
    private ILocationService locationService;

    @Override
    public SipResponse handle(SipRequest message) {

        // 解析请求头和其他必要信息
        SipAddress messageTo = message.getTo();
        SipAddress messageFrom = message.getFrom();
        String callId = message.getCallId();
        String cSeq = message.getCSeq();
        String event = message.getHeaders().get("Event");
        String expires = message.getHeaders().get("Expires");
        // 检查事件类型
        if (event == null || !isValidEvent(event)) {
            SipResponse response = createResponse(489, message, "Bad Event");
            return response;
        }

        // 检查订阅者身份验证
        if (!isAuthenticated(messageFrom)) {
            SipResponse response = createResponse(401, message, "Unauthorized");
            return response;
        }

        // 检查订阅期限
        int expiresValue = expires != null ? Integer.parseInt(expires) : 3600;
        if (expiresValue <= 0) {
            SipResponse response = createResponse(400, message, "Invalid Expires Header");
            return response;
        }
        // 如果所有检查通过，生成并发送200 OK响应
        return createResponse(200, message, "Subscription accepted");
    }

    private boolean isAuthenticated(SipAddress messageFrom) {
        String username = extractUsername(messageFrom.getUri());
        return locationService.checkUserName(username);
    }

    /**
     * presence：用于呈现用户的在线状态。通常用于即时通讯（IM）系统，以通知其他用户某个用户的在线或离线状态
     * dialog：用于监视对话状态，例如正在进行的通话的开始和结束
     * refer：用于REFER方法，指示资源转移的状态
     * conference：用于会议状态的更新，例如参与者的加入和离开
     * message-summary：用于通知消息等待指示（MWI），如未读的语音邮件
     * reg：用于监视用户注册状态
     * @param event
     * @return
     */
    private boolean isValidEvent(String event) {
        String eventStr = "presence|dialog|refer|conference|message-summary|reg";
        // 检查事件类型是否有效（根据实际需求实现）
        return eventStr.contains(event.toLowerCase());
    }
}
