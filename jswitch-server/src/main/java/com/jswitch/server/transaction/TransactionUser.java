package com.jswitch.server.transaction;

import com.jswitch.server.msg.SipMessageEvent;

/**
 * 事务用户TU (Transaction User)
 * 每个SIP实体，除无状态代理外，都是事务用户。
 * 当事务用户想发送请求时，它就创建一个客户端事务实例，并将请求与目的IP 地址、端口一起发送。创建客户端事务的TU 也可以取消事务。
 * 客户端取消事务的时候，就要求服务器停止进一步处理，并恢复到初始化事务前的状态，然后返回该事务的一个错误响应。
 * 可通过CANCEL请求完成取消事务，CANCEL请求包含自己的事务，同时也提及需要取消的事务
 */
public interface TransactionUser {

    public void handleRequest(SipMessageEvent event);

    void sendRequest(SipMessageEvent event);

    void cancelTransaction(SipMessageEvent event);

    void sendResponse(SipMessageEvent event);

    void sendResponseError(SipMessageEvent event);
}
