package com.jswitch.server.process.requset;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.server.process.AbstractSipMessageProcess;
import com.jswitch.server.utils.SessionChannelManager;
import com.jswitch.service.domain.Location;
import com.jswitch.sip.*;
import com.jswitch.sip.adress.AddressImpl;
import com.jswitch.sip.header.*;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Slf4j
@Component("REGISTER")
public class RegisteredSipMessageProcess extends AbstractSipMessageProcess {


    @Override
    public void handler(SipMessageEvent event) {
        SipRequest sipRequest = (SipRequest)event.getMessage();

        //判断是否为
        if(!sipRequest.getRequestLine().getUri().isSipURI()){
            SipResponse response = sipRequest.createResponse(SipResponseStatus.FORBIDDEN.getStatusCode());
            event.getCtx().writeAndFlush(response);
        }
        if(checkAuthorization(sipRequest)){
            //注册
            Location location = new Location();
            location.setContact(sipRequest.getContactHeader().getAddress().toString());
            location.setCallId(sipRequest.getCallId().getCallId());
            location.setCseq(Long.valueOf(sipRequest.getCSeq().getSeqNumber()).intValue());
            location.setUsername(sipRequest.getFrom().getAddress().getDisplayName());
            location.setDomain(((AddressImpl)sipRequest.getFrom().getAddress()).getHost());
            location.setReceived(((AddressImpl)sipRequest.getTo().getAddress()).getHost());
            location.setStatus(0);

            int expires = sipRequest.getContactHeader().getExpires() < 0?sipRequest.getExpires().getExpires():sipRequest.getContactHeader().getExpires();
            if(expires > 0){
                location.setExpires(DateUtil.offsetSecond(new Date(),expires));
                SessionChannelManager.add(location.getUsername(), event.getCtx());
                locationService.add(location);
            } else {
                SessionChannelManager.remove(location.getUsername());
                locationService.delete(location);
            }
            SipResponse response = sipRequest.createResponse(SipResponseStatus.OK.getStatusCode());
            ContactHeader contactHeader = headerFactory.createContactHeader(sipRequest.getTo().getAddress());
            contactHeader.setExpires(expires);
            response.setHeader(contactHeader);
            DateHeader dateHeader = headerFactory.createDateHeader(Calendar.getInstance());
            response.setHeader(dateHeader);
            ChannelFuture channelFuture = event.getCtx().writeAndFlush(response);
            if(channelFuture.isDone()){
                log.info("注册失败");
            }
        }else {
            SipResponse response = sipRequest.createResponse(SipResponseStatus.UNAUTHORIZED.getStatusCode());
            try {
                String realm = ((AddressImpl) sipRequest.getFrom().getAddress()).getHost();
                String digest = "Digest realm=\"" + realm + "\", nonce=\"" + IdUtil.fastSimpleUUID() + "\"";
                WWWAuthenticateHeader digestHeader = headerFactory.createWWWAuthenticateHeader(digest);
                response.setHeader(digestHeader);
            } catch (ParseException e) {

            }
            ChannelFuture channelFuture = event.getCtx().writeAndFlush(response);
            if(channelFuture.isDone()){
                log.info("注册失败");
            }
        }
    }

    @Override
    public Response handleRequest(SipMessageEvent event) {
        return null;
    }


}
