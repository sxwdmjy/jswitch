package com.jswitch.server.channel;

import cn.hutool.core.collection.CollectionUtil;
import com.jswitch.sip.SipMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author danmo
 * @date 2024-06-04 17:29
 **/
@Data
public class SipCallChannel {

    /**
     * 呼叫id
     */
    private String callId;

    /**
     * 呼叫通道ID
     */
    private String fromChannelId;

    /**
     * 被叫通道ID
     */
    private String toChannelId;

    /**
     * 主叫号码
     */
    private String caller;

    /**
     * 被叫号码
     */
    private String callee;

    /**
     * 呼叫状态
     */
    private Integer status;

    /**
     * 开始呼叫时间
     */
    private Long callTime;

    /**
     * 结束时间
     */
    private Long endTime;

    private List<SipMessage> sipMessages;

    private Map<String, SipCallDetailChannel> channelMap;


    public void addMessage(SipMessage sipMessage) {
        if (CollectionUtil.isEmpty(this.sipMessages)) {
            this.sipMessages = new ArrayList<>();
        }
        this.sipMessages.add(sipMessage);
    }

    public void setChannelInfoMap(String uniqueId, SipCallDetailChannel channelInfo) {
        if(CollectionUtil.isEmpty(channelMap)){
            channelMap = new HashMap<>();
        }
        channelMap.put(uniqueId,channelInfo);
    }

}
