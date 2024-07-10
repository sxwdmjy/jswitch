package com.jswitch.sip;

import com.jswitch.sip.adress.Address;
import com.jswitch.sip.header.CSeqHeader;
import com.jswitch.sip.header.CallIdHeader;
import com.jswitch.sip.header.RouteList;
import lombok.Data;

import java.util.List;

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

    private List<SipMessage> messages;
    /**
     * 是否安全
     */
    private boolean secure;


    public static SipDialog createDialogFromInvite(SipRequest inviteRequest) {
        SipDialog sipDialog = new SipDialog();
        sipDialog.setDialogId(inviteRequest.getDialogId(false));
        sipDialog.setCallId(inviteRequest.getCallId());
        sipDialog.setLocalTag(inviteRequest.getFromTag());
        sipDialog.setRemoteTag(inviteRequest.getToTag());
        sipDialog.setLocalUrl(inviteRequest.getTo().getAddress());
        sipDialog.setRemoteUrl(inviteRequest.getFrom().getAddress());
        sipDialog.setRemoteTarget(inviteRequest.getContactHeader().getAddress());
        return sipDialog;
    }

    public static SipDialog createDialogFromResponse(SipResponse response, SipRequest inviteRequest) {
        SipDialog sipDialog = new SipDialog();
        sipDialog.setDialogId(response.getDialogId(true));
        sipDialog.setCallId(response.getCallId());
        if(response.getStatusCode() >= 100 && response.getStatusCode() < 200){
            sipDialog.setState(DialogState.EARLY);
        }else if(response.getStatusCode() >= 200 && response.getStatusCode() < 300){
            sipDialog.setState(DialogState.CONFIRMED);
        }else if(response.getStatusCode() >= 300 && response.getStatusCode() < 700){
            sipDialog.setState(DialogState.COMPLETED);
        }else{
            sipDialog.setState(DialogState.TERMINATED);
        }
        sipDialog.setLocalTag(inviteRequest.getFromTag());
        sipDialog.setRemoteTag(response.getToTag());
        sipDialog.setLocalUrl(response.getTo().getAddress());
        sipDialog.setRemoteUrl(inviteRequest.getFrom().getAddress());
        sipDialog.setRemoteTarget(inviteRequest.getContactHeader().getAddress());
        return sipDialog;
    }
}
