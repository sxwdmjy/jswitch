package com.jswitch.sip;

import java.io.Serializable;

/**
 * 事务
 *
 * @author danmo
 * @version 1.0
 * @since 2024-06-10
 */
public interface Transaction extends Serializable {

    public Dialog getDialog();

    /**
     * 标识此事务的唯一分支标识符,分支标识符用于ViaHeader
     * 分支ID参数的唯一性,不是RFC2543的一部分
     * 符合RFC3261规范的元素插入的分支ID必须始终以字符“z9hG4bK”开头,这7个字符用作魔术cookie以便接收请求的服务器可以确定分支ID的构造为全局唯一分支令牌的精确格式由实现定义
     * 此方法应始终为同一事务返回相同的分支标识符
     *
     * @return 唯一标识此事务的新分支
     */
    public String getBranchId();


    /**
     * 获取事务的当前状态
     * 如果尚未使用ClientTransaction发送消息，则返回 null
     *
     * @return
     */
    public TransactionState getState();

    /**
     * 获取事务的请求
     *
     * @return
     */
    public Request getRequest();

    /**
     * 终结事务
     */
    public void terminate();
}
