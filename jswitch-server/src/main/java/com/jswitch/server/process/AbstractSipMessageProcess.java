package com.jswitch.server.process;

import com.jswitch.server.cache.SipChannelCache;
import com.jswitch.server.factory.SipMessageStrategy;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.utils.SipMsgBuildUtils;
import com.jswitch.service.service.ILocationService;
import com.jswitch.service.service.ISubscriberService;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public abstract class AbstractSipMessageProcess implements SipMessageStrategy {

    @Resource
    protected ISubscriberService subscriberService;

    @Resource
    protected ILocationService locationService;

    @Autowired
    private SipMsgBuildUtils sipMsgBuildUtils;



    protected String extractUsername(String sipUri) {
        return sipMsgBuildUtils.extractUsername(sipUri);
    }

    protected String getPasswordForUser(String username) {
        return sipMsgBuildUtils.getPasswordForUser(username);
    }


    protected Map<String, String> parseAuthorizationHeader(String authHeader) {
        return sipMsgBuildUtils.parseAuthorizationHeader(authHeader);
    }

    //身份验证
    protected Boolean checkAuthorization(SipRequest request) {
        return sipMsgBuildUtils.checkAuthorization(request);
    }

    protected SipResponse createTryingResponse(SipRequest request) {
        return sipMsgBuildUtils.createTryingResponse(request);
    }

    protected SipResponse createOkResponse(SipRequest request) {
        return sipMsgBuildUtils.createOkResponse(request);
    }

    protected SipResponse createResponse(int statusCode, SipRequest request, String reasonPhrase) {
        return sipMsgBuildUtils.createResponse(statusCode, request, reasonPhrase);
    }

    protected SipResponse createUnauthorizedResponse(SipRequest request) {
        return sipMsgBuildUtils.createUnauthorizedResponse(request);
    }

    protected SipRequest createInviteRequest(SipRequest request) {
        return sipMsgBuildUtils.createInviteRequest(request);
    }


    protected void sendResponse(ChannelHandlerContext ctx, String sipResponse) {
        log.info("send response:{}", sipResponse);
        ctx.writeAndFlush(sipResponse);
    }

    @Override
    public void handle(SipMessageEvent event) throws InterruptedException {

    }
}
