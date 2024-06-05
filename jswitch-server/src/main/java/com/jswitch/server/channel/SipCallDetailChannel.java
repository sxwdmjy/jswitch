package com.jswitch.server.channel;

import lombok.Data;

@Data
public class SipCallDetailChannel {

    /**
     * 呼叫id
     */
    private String callId;
    /**
     * 唯一标识
     */
    private String uniqueId;

    /**
     * 其他唯一标识
     */
    private String otherUniqueId;

    /**
     * 呼叫发起方
     */
    private String host;

    /**
     * 呼叫开始时间
     */
    private Long callTime;

    /**
     * 振铃开始时间
     */
    private Long ringStartTime;

    /**
     * 振铃结束时间
     */
    private Long ringEndTime;

    /**
     * 接通时间
     */
    private Long answerTime;

    /**
     * 桥接时间
     */
    private Long bridgeTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 当前设备状态 0-在线 1-正在呼叫 2-振铃中
     */
    private Integer status;
}
