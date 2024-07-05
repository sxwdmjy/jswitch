package com.jswitch.sip;

import com.jswitch.sip.adress.Address;
import com.jswitch.sip.header.CSeqHeader;
import com.jswitch.sip.header.CallIdHeader;
import com.jswitch.sip.header.RouteList;
import lombok.Data;

/**
 * @author danmo
 * @date 2024-06-20 16:14
 **/
@Data
public class SipDialog implements Dialog {

    /**
     * 对话标识ID,这个ID由Call-ID，和一个本地tag和远程tag组成
     */
    private String dialogId;
    /**
     * Call-ID
     */
    private CallIdHeader callId;
    /**
     * 对话状态
     */
    private DialogState state;

    /**
     * 路由列表
     */
    protected RouteList routeList;
    /**
     * 本地CSeq
     */
    private CSeqHeader localCSeq;
    /**
     * 远程CSeq
     */
    private CSeqHeader remoteCSeq;

    /**
     * 远程Target
     */
    private Address remoteTarget;

    private String localTag;

    private String remoteTag;

    private Address localUrl;

    private Address remoteUrl;

    private URI localParty;

    private URI remoteParty;
    /**
     * 是否安全
     */
    private boolean secure;

}
