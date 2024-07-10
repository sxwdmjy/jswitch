package com.jswitch.server.transaction;

import com.jswitch.server.msg.SipMessageEvent;
import com.jswitch.sip.SipRequest;
import com.jswitch.sip.SipResponse;
import io.netty.channel.ChannelHandlerContext;

/**
 * 事务用户TU (Transaction User)
 */
public interface TransactionUser {


    /**
     * 接收并处理请求。
     *
     * @param request     收到的SIP请求
     * @param transaction 服务器事务实例
     */
    void onRequest(SipRequest request, ServerSipTransaction transaction);

    /**
     * 接收并处理响应。
     *
     * @param response    收到的SIP响应
     * @param transaction 客户端事务实例
     */
    void onResponse(SipResponse response, ClientSipTransaction transaction);

    /**
     * 当事务超时时调用该方法。
     *
     * @param transaction 超时的事务
     */
    void onTimeout(Transaction transaction);

    /**
     * 取消事务。
     *
     * @param transaction 要取消的客户端事务
     */
    void onCancel(Transaction transaction);

    /**
     * 发送请求。
     *
     * @param request 要发送的SIP请求
     * @param ctx     通道上下文
     */
    void sendRequest(SipRequest request, ChannelHandlerContext ctx);

    void sendResponseError(SipMessageEvent event);
}
